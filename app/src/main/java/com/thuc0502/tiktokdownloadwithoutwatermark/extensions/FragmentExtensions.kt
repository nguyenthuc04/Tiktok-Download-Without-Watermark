package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

inline fun <reified T> Fragment.argument(key: String): T? {
    return arguments?.get(key) as? T
}

fun Fragment.withArguments(vararg pairs: Pair<String, Any?>): Fragment {
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
    arguments = bundle
    return this
}

fun FragmentActivity.addFragment(fragment: Fragment ,containerId: Int ,tag: String? = null) {
    supportFragmentManager.beginTransaction()
        .add(containerId, fragment, tag)
        .addToBackStack(tag)
        .commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment ,frameId: Int) {
    supportFragmentManager.beginTransaction()
        .replace(frameId, fragment)
        .commit()
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    parentFragmentManager.beginTransaction()
        .replace(frameId, fragment)
        .commit()
}