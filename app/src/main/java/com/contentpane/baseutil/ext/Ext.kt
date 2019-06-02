package com.contentpane.baseutil.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes

fun View.dp2px(dp: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun View.px2dp(px: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

var showToast: Toast? = null
fun Context.toast(content: String) {
    showToast?.apply {
        setText(content)
        show()
    } ?: run {
        Toast.makeText(this.applicationContext, content, Toast.LENGTH_SHORT).apply {
            showToast = this
        }.show()
    }
}

/**
 * show toast
 * @param id strings.xml
 */
fun Context.toast(@StringRes id: Int) {
    toast(getString(id))
}

fun Context.openBrowser(url: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).run {  startActivity(this)}
}