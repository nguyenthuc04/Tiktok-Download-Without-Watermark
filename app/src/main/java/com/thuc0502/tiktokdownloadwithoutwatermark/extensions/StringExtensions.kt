package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.isEmailValid(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPhoneNumberValid(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

fun String.colorize(color: Int): SpannableString {
    val spannable = SpannableString(this)
    spannable.setSpan(ForegroundColorSpan(color) , 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}

fun String.toColorInt(): Int {
    return try {
        Color.parseColor(this)
    } catch (e: IllegalArgumentException) {
        // Handle invalid color
        Color.TRANSPARENT
    }
}

/**
 * Chuyển đổi String thành Date
 */
fun String.toDate(format: String, locale: Locale = Locale.getDefault()): Date? {
    return try {
        SimpleDateFormat(format, locale).parse(this)
    } catch (e: Exception) {
        null
    }
}