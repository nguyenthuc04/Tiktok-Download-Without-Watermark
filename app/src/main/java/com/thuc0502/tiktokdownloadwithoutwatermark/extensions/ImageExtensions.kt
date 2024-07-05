package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

fun ImageView.loadUrlWithPicasso(url: String) {
    Picasso.get()
        .load(url)
        .into(this)
}