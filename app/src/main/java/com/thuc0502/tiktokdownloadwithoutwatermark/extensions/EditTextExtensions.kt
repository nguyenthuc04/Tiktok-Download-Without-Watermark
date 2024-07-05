package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.util.Patterns
import android.widget.EditText

val EditText.value: String
    get() = text.toString()

fun EditText.isEmpty(): Boolean {
    return text.isEmpty()
}

fun EditText.isNotEmpty(): Boolean {
    return text.isNotEmpty()
}

fun EditText.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this.text.toString()).matches()
}