/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.view.circleswipelayout

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Shader
import android.graphics.drawable.Drawable

// https://github.com/salih-demir/swipe-layout
class CircularDrawable(bitmap: Bitmap) : Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    var radius = intrinsicWidth.toFloat() / 2
        set(value) {
            field = value
            invalidateSelf()
        }

    override fun draw(canvas: Canvas) {
        val width = bounds.width()
        val height = bounds.height()

        val centerX = width / 2f
        val centerY = height / 2f

        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity() = PixelFormat.TRANSPARENT

    fun setMatrix(matrix: Matrix) {
        paint.shader.setLocalMatrix(matrix)
        invalidateSelf()
    }
}
