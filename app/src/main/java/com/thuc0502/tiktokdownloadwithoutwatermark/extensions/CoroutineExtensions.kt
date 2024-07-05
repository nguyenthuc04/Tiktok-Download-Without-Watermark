package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import kotlinx.coroutines.delay

suspend fun repeatWithDelay(times: Int, delayMillis: Long, action: () -> Unit) {
    repeat(times) {
        action()
        delay(delayMillis)
    }
}