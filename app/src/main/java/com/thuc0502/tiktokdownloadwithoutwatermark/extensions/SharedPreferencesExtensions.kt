package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.content.SharedPreferences

fun SharedPreferences.put(key: String, value: Any) {
    val editor = edit()
    when (value) {
        is String -> editor.putString(key, value)
        is Int -> editor.putInt(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Float -> editor.putFloat(key, value)
        is Long -> editor.putLong(key, value)
        else -> throw IllegalArgumentException("Unsupported type")
    }
    editor.apply()
}

inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1L) as T
        else -> throw IllegalArgumentException("Unsupported type")
    }
}

fun SharedPreferences.Editor.removeKeys(vararg keys: String) {
    keys.forEach { this.remove(it) }
    apply()
}