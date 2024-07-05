package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.showToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: CharSequence) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}