package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun FragmentManager.addFragment(fragment: Fragment, frameId: Int) {
    inTransaction { add(frameId, fragment) }
}

fun FragmentManager.replaceFragment(fragment: Fragment, frameId: Int) {
    inTransaction { replace(frameId, fragment) }
}

fun FragmentManager.removeFragment(fragment: Fragment) {
    inTransaction { remove(fragment) }
}

