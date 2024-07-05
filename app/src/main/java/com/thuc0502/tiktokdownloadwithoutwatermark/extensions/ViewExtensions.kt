package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.SystemClock
import android.view.View

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.setDebounceClickListener(interval: Long = 600L, onClick: (View) -> Unit) {
    var lastClickTime = 0L
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime >= interval) {
            lastClickTime = SystemClock.elapsedRealtime()
            onClick(it)
        }
    }
}

fun View.setOnDebounceClickListener(debounceTime: Long = 600L, action: () -> Unit) {
    var lastClickTime = 0L
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= debounceTime) {
            lastClickTime = currentTime
            action()
        }
    }
}

/**
 * Làm cho một lượt xem chớp mắt
 */
fun View.blink() {
    ObjectAnimator.ofFloat(this, "alpha", 0.2f, 1.0f)
        .apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
}

/**
 * Thiết lập OnClickListener với việc chuyển hướng sang Activity khác
 */
inline fun <reified T> View.navigateTo() {
    setOnClickListener {
        context.startActivity(Intent(context, T::class.java))
    }
}