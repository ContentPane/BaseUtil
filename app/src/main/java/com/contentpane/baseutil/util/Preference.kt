package com.contentpane.baseutil.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.contentpane.baseutil.App
import java.io.*
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 使用方法：
 * private var isLogin by Preference(Preference.IS_LOGIN, false)
 * private var userJson by Preference(Preference.USER_GSON, "")
 *
 */

class Preference<T>(val name: String, private val default: T) : ReadWriteProperty<Any?, T> {

    companion object {
        const val IS_LOGIN = "is_login"
        const val USER_GSON = "user_gson"
    }

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.CONTEXT)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putValue(name, value)
    }

    /**
     * 序列化对象
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(name: String, default: T): T = with(prefs) {
        val result: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> doSerialization(getString(name, serialize(default)) ?: "")
        }
        return result as T
    }

    private fun <T> putValue(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()
    }

    /**
     * 反序列对象
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> doSerialization(str: String): A {
        // 解码之后的字符
        val redStr = URLDecoder.decode(str, "UTF-8")
        val byteArrayStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1"))
        )
        val objectInputStream = ObjectInputStream(byteArrayStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayStream.close()
        return obj
    }

    /**
     * 序列化对象
     */
    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 删除全部数据
     */
    fun clearPreference() {
        prefs.edit().clear().apply()
    }

    /**
     * 根据key删除存储数据
     */
    fun clearPreference(key: String) {
        prefs.edit().remove(key).apply()
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(): Map<String, *> {
        return prefs.all
    }
}