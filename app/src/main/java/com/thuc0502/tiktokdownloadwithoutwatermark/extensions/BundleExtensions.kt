package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.os.Bundle

fun bundleOf(vararg pairs: Pair<String, Any>): Bundle {
    return Bundle().apply {
        for (pair in pairs) {
            val key = pair.first
            when (val value = pair.second) {
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw IllegalArgumentException("Unsupported bundle component (${value::class.java})")
            }
        }
    }
}