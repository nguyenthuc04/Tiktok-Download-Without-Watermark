package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.app.Activity
import android.view.inputmethod.InputMethodManager

/**
 * Ẩn bàn phím
 */
fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}