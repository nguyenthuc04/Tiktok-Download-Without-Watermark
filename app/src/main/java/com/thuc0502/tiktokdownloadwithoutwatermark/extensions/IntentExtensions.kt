package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.content.Intent
import android.os.Bundle

/**
 * Thêm dữ liệu vào Intent một cách tiện lợi
 */
fun Intent.putExtras(vararg pairs: Pair<String, Any?>): Intent {
    val bundle = Bundle()
    for (pair in pairs) {
        val key = pair.first
        val value = pair.second
        when (value) {
            is Int -> bundle.putInt(key, value)
            is String -> bundle.putString(key, value)
            is Boolean -> bundle.putBoolean(key, value)
            is Float -> bundle.putFloat(key, value)
            is Long -> bundle.putLong(key, value)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
    putExtras(bundle)
    return this
}