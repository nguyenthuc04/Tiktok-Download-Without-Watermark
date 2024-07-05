package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.content.Context
import android.net.Uri
import java.io.File

/**
 * Chuyển đổi Uri thành File
 */
fun Uri.toFile(context: Context): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(this)
        val file = File(context.cacheDir, System.currentTimeMillis().toString())
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}