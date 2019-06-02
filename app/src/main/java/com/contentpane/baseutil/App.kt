package com.contentpane.baseutil

import android.app.Application
import android.content.Context
import com.contentpane.baseutil.bean.User
import kotlin.properties.Delegates

class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
        lateinit var CURRENT_USER: User
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
    }

}