package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer { it?.let { observer(it) } })
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

/**
 * Kiểm tra LiveData có dữ liệu hay không
 * @return true nếu LiveData có dữ liệu, ngược lại trả về false
 */
fun <T> LiveData<T>.hasActiveObservers(): Boolean {
    return this.hasActiveObservers()
}