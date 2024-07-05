package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat

fun Context.color(resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

fun Context.drawable(resId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resId)
}

/**
 *Lấy tên của ứng dụng
 */
fun Context.getAppName(): String {
    val applicationInfo = applicationInfo
    val stringId = applicationInfo.labelRes
    return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else getString(stringId)
}


fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.activeNetworkInfo?.isConnected ?: false
}

