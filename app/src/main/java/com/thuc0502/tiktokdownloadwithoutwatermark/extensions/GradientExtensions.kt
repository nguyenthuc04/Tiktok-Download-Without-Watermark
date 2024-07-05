package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Thiết lập Linear Gradient Background cho View với Alpha và Nhiều Thuộc Tính Khác
 */
fun View.setLinearGradientBackground(
    colors: IntArray,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    alpha: Int = 255,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(orientation, colors)
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}

fun View.setRadialGradientBackground(
    colors: IntArray,
    radius: Float,
    alpha: Int = 255,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors).apply {
        gradientType = GradientDrawable.RADIAL_GRADIENT
        gradientRadius = radius
        setGradientCenter(centerX, centerY)
    }
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}

fun View.setSweepGradientBackground(
    colors: IntArray,
    alpha: Int = 255,
    centerX: Float = 0.5f,
    centerY: Float = 0.5f,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors).apply {
        gradientType = GradientDrawable.SWEEP_GRADIENT
        setGradientCenter(centerX, centerY)
    }
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}

/**
 * Thiết lập Linear Gradient Text cho TextView với Alpha
 */
fun TextView.setLinearGradientText(startColor: Int ,endColor: Int ,alpha: Int = 255) {
    val width = paint.measureText(text.toString())
    val textShader = LinearGradient(
        0f, 0f, width, textSize,
        intArrayOf(startColor, endColor),
        null, Shader.TileMode.CLAMP
    )
    paint.shader = textShader
    paint.alpha = alpha
}

/**
 * Thiết lập Linear Gradient Background cho Button với Alpha và Nhiều Thuộc Tính Khác
 */
fun Button.setLinearGradientBackground(
    colors: IntArray,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    alpha: Int = 255,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(orientation, colors)
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}

/**
 * Thiết lập Linear Gradient Background cho LinearLayout với Alpha và Nhiều Thuộc Tính Khác
 */
fun LinearLayout.setLinearGradientBackground(
    colors: IntArray,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    alpha: Int = 255,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(orientation, colors)
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}

/**
 * Thiết lập Linear Gradient Background cho RelativeLayout với Alpha và Nhiều Thuộc Tính Khác
 */
fun RelativeLayout.setLinearGradientBackground(
    colors: IntArray,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    alpha: Int = 255,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(orientation, colors)
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}

/**
 * Thiết lập Linear Gradient Background cho ConstraintLayout với Alpha và Nhiều Thuộc Tính Khác
 */
fun ConstraintLayout.setLinearGradientBackground(
    colors: IntArray,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    alpha: Int = 255,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(orientation, colors)
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}

/**
 * Thiết lập Linear Gradient Background cho ImageView với Alpha và Nhiều Thuộc Tính Khác
 */
fun ImageView.setLinearGradientBackground(
    colors: IntArray,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    alpha: Int = 255,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(orientation, colors)
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}

/**
 * Thiết lập Linear Gradient Background cho CardView với Alpha và Nhiều Thuộc Tính Khác
 */
fun CardView.setLinearGradientBackground(
    colors: IntArray,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    alpha: Int = 255,
    cornerRadius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = 0
) {
    val gradient = GradientDrawable(orientation, colors)
    gradient.alpha = alpha
    gradient.cornerRadius = cornerRadius
    if (strokeWidth > 0) {
        gradient.setStroke(strokeWidth, strokeColor)
    }
    this.background = gradient
}