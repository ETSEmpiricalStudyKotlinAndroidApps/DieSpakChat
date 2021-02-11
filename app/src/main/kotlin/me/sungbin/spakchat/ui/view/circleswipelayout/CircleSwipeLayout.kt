/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.view.circleswipelayout

import android.animation.Animator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.annotation.FloatRange
import androidx.core.view.GestureDetectorCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import me.sungbin.spakchat.ui.view.circleswipelayout.transition.AnimationListener
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.properties.Delegates

// https://github.com/salih-demir/swipe-layout
class CircleSwipeLayout : FrameLayout, GestureDetector.OnGestureListener {

    companion object {
        @FloatRange(from = 0.0, to = 1.0, fromInclusive = false, toInclusive = false)
        private const val MOVE_PROGRESS_OFFSET = 0.5f
        private const val MIN_CIRCULAR_WIDTH_MULTIPLIER = 0.12f
        private const val MOVE_LAYER_PROGRESS_MULTIPLIER = 0.8f
        private const val CIRCULAR_DRAWABLE_LAYER_INDEX = 1
        private const val DEFAULT_ANIMATION_DURATION_IN_MS = 200L
    }

    private val gestureDetector = GestureDetectorCompat(context, this).apply {
        setIsLongpressEnabled(false)
    }

    private val stateAnimTypeEvaluator: TypeEvaluator<Array<Float>> =
        TypeEvaluator { fraction, startValues, endValues ->
            val leftStart = startValues[0]
            val topStart = startValues[1]
            val radiusStart = startValues[2]
            val alphaStart = startValues[3]

            val leftEnd = endValues[0]
            val topEnd = endValues[1]
            val radiusEnd = endValues[2]
            val alphaEnd = endValues[3]

            val targetLeft = leftStart + ((leftEnd - leftStart) * fraction)
            val targetTop = topStart + ((topEnd - topStart) * fraction)
            val targetRadius = radiusStart + ((radiusEnd - radiusStart) * fraction)
            val targetAlpha = alphaStart + ((alphaEnd - alphaStart) * fraction)

            arrayOf(targetLeft, targetTop, targetRadius, targetAlpha)
        }
    private val decelerateInterpolator = DecelerateInterpolator()

    private var lastDownPoint: PointF? = null
    private var lastScrollPoint: PointF? = null
    private var lastProgressBeforeMovePoint: PointF? = null

    private val dimDrawable = ColorDrawable(Color.BLACK)
    private var circularDrawable: CircularDrawable? = null
    private var defaultForeground: Drawable? = null
    private var animatedForeground: LayerDrawable? = null

    private var isMoveToStateAnimationPresent = false

    private var progress: Float by Delegates.observable(0f) { _, old, new ->
        if (old == 0f && new > 0f) circleSwipeListener?.onSwipeStarted()
        handleProgressChange(new)
    }

    var circleSwipeListener: CircleSwipeListener? = null
    var autoReset = true
    var swipeEnabled = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        isClickable = true
        isFocusable = true
    }

    override fun dispatchDraw(canvas: Canvas?) {
        if (!isTransitionPresent()) super.dispatchDraw(canvas)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (swipeEnabled && !isMoveToStateAnimationPresent) {
            when (ev.actionMasked) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                    if (isTransitionPresent()) {
                        if (progress > MOVE_PROGRESS_OFFSET) {
                            circleSwipeListener?.onSwiped()
                        } else {
                            circleSwipeListener?.onSwipeCancelled()
                        }
                        if (autoReset) {
                            resetTransition()
                        }
                    }
            }
            gestureDetector.onTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onSingleTapUp(ev: MotionEvent) = false

    override fun onDown(e: MotionEvent): Boolean {
        lastDownPoint = PointF(e.x, e.y)
        initializeTransition()
        return false
    }

    override fun onFling(
        ev1: MotionEvent,
        ev2: MotionEvent,
        velocityX: Float,
        velocityY: Float,
    ) = if (velocityY > 1000) {
        circleSwipeListener?.onSwipedFling()
        true
    } else {
        false
    }

    override fun onScroll(
        ev1: MotionEvent,
        ev2: MotionEvent,
        distanceX: Float,
        distanceY: Float,
    ): Boolean {
        lastScrollPoint = PointF(ev2.x, ev2.y)

        val scrolledDistance = ev2.y - lastDownPoint!!.y
        var totalScrollDistance = height - lastDownPoint!!.y
        if (totalScrollDistance < height / 5f)
            totalScrollDistance = height / 5f

        val progress = scrolledDistance / totalScrollDistance
        return if (progress in 0.0f..1.0f) {
            this.progress = progress
            true
        } else {
            false
        }
    }

    override fun onLongPress(ev: MotionEvent) = Unit
    override fun onShowPress(ev: MotionEvent) = Unit

    override fun setForeground(foreground: Drawable?) {
        defaultForeground = foreground
        super.setForeground(foreground)
    }

    private fun showBackground() {
        background?.alpha = 255
    }

    private fun hideBackground() {
        background?.alpha = 0
    }

    fun isTransitionPresent() = progress != 0f
    private fun getDimAlpha() = dimDrawable.alpha
    private fun getRadius() = circularDrawable!!.radius

    private fun setDimAlpha(alpha: Int) {
        dimDrawable.alpha = alpha
    }

    private fun setRadius(radius: Float) {
        circularDrawable!!.radius = radius
        animatedForeground = LayerDrawable(arrayOf(dimDrawable, circularDrawable))
        super.setForeground(animatedForeground)
    }

    private fun updateCircularDrawableLayerInset(left: Int, top: Int, right: Int, bottom: Int) {
        animatedForeground = LayerDrawable(arrayOf(dimDrawable, circularDrawable))
        animatedForeground!!.setLayerInset(CIRCULAR_DRAWABLE_LAYER_INDEX, left, top, right, bottom)
        super.setForeground(animatedForeground)
    }

    private fun updateCircularDrawableMatrixForLayerInset(left: Int, top: Int) {
        val matrixLeft = -left / 2f
        val matrixTop = -top / 2f
        val matrix = Matrix().apply {
            setTranslate(matrixLeft, matrixTop)
        }
        circularDrawable!!.setMatrix(matrix)
        invalidate()
    }

    private fun handleProgressChange(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        if (progress == 0f) {
            removeTransition()
        } else {
            hideBackground()

            val reversedProgress = 1 - progress

            val interpolatedProgress = decelerateInterpolator.getInterpolation(reversedProgress)
            val dimAlpha = (255 * interpolatedProgress).toInt()
            setDimAlpha(dimAlpha)

            when {
                progress <= MOVE_PROGRESS_OFFSET -> {
                    lastProgressBeforeMovePoint = lastScrollPoint

                    val halfWidth = width / 2.0f
                    val halfHeight = height / 2.0f
                    val maxRadius = sqrt(halfWidth.pow(2) + halfHeight.pow(2))
                    val minRadius = halfWidth - 1
                    val rangedProgress = progress * 2

                    val radius = maxRadius - (maxRadius - minRadius) * rangedProgress
                    setRadius(radius)

                    updateCircularDrawableMatrixForLayerInset(0, 0)
                }
                else -> {
                    val minRadius = width * MIN_CIRCULAR_WIDTH_MULTIPLIER
                    var radius = width * reversedProgress
                    if (radius < minRadius) radius = minRadius
                    setRadius(radius)

                    val halfWidth = width / 2f
                    val halfHeight = height / 2f

                    val lastProgressBeforeMoveLeft = lastProgressBeforeMovePoint!!.x
                    val lastProgressBeforeMoveTop = lastProgressBeforeMovePoint!!.y

                    val differenceLeft = (lastScrollPoint!!.x - lastProgressBeforeMoveLeft)
                    val differenceTop = (lastScrollPoint!!.y - lastProgressBeforeMoveTop)

                    val progressLeft =
                        if (differenceLeft < 0) differenceLeft / lastProgressBeforeMoveLeft
                        else differenceLeft / (width - lastProgressBeforeMoveLeft)
                    val progressTop =
                        if (differenceTop < 0) differenceTop / lastProgressBeforeMoveTop
                        else differenceTop / (height - lastProgressBeforeMoveTop)

                    val targetLeft =
                        -(progressLeft * MOVE_LAYER_PROGRESS_MULTIPLIER * halfWidth * 2f).toInt()
                    val targetTop =
                        -(progressTop * MOVE_LAYER_PROGRESS_MULTIPLIER * halfHeight * 2f).toInt()

                    updateCircularDrawableLayerInset(targetLeft, targetTop, 0, 0)
                    updateCircularDrawableMatrixForLayerInset(targetLeft, targetTop)
                }
            }
        }
    }

    private fun getViewVisual(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)

        return bitmap
    }

    private fun initializeTransition() {
        circularDrawable = CircularDrawable(getViewVisual(this))
    }

    private fun removeTransition() {
        super.setForeground(defaultForeground)
        showBackground()
    }

    private fun resetTransition() {
        val changeProgress = ValueAnimator.ofFloat(progress, 0f)
        changeProgress.duration = DEFAULT_ANIMATION_DURATION_IN_MS
        changeProgress.addUpdateListener {
            progress = it.animatedValue as Float
        }
        changeProgress.addListener(object : AnimationListener() {
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                circleSwipeListener?.onSwipeReset()
            }
        })
        changeProgress.start()
    }

    fun moveCircleToState(left: Int, top: Int, radius: Int, dimAlpha: Int): Animator? {
        if (!isTransitionPresent() && progress > MOVE_PROGRESS_OFFSET) return null

        val currentRadius = getRadius()
        val currentAlpha = getDimAlpha().toFloat()
        val currentForeground = foreground as LayerDrawable

        val halfWidth = width / 2f
        val halfHeight = height / 2f

        val insetLeft = currentForeground.getLayerInsetLeft(CIRCULAR_DRAWABLE_LAYER_INDEX)
        val insetTop = currentForeground.getLayerInsetTop(CIRCULAR_DRAWABLE_LAYER_INDEX)

        val startLeft = halfWidth + -insetLeft / 2f - currentRadius
        val startTop = halfHeight + -insetTop / 2f - currentRadius

        val endLeft = left.toFloat()
        val endTop = top.toFloat()

        val startValues = arrayOf(startLeft, startTop, currentRadius, currentAlpha)
        val endValues = arrayOf(endLeft, endTop, radius.toFloat(), dimAlpha.toFloat())

        val moveImage = ValueAnimator.ofObject(stateAnimTypeEvaluator, startValues, endValues)
        moveImage.duration = DEFAULT_ANIMATION_DURATION_IN_MS
        moveImage.interpolator = FastOutSlowInInterpolator()
        moveImage.addUpdateListener {
            val animatedValue = it.animatedValue
            if (animatedValue is Array<*>) {
                val animLeft = animatedValue[0] as Float
                val animTop = animatedValue[1] as Float
                val animRadius = animatedValue[2] as Float
                val animAlpha = animatedValue[3] as Float

                val animInsetLeft = ((-animLeft + halfWidth - animRadius) * 2).toInt()
                val animInsetTop = ((-animTop + halfHeight - animRadius) * 2).toInt()

                setDimAlpha(animAlpha.toInt())
                setRadius(animRadius)
                updateCircularDrawableLayerInset(animInsetLeft, animInsetTop, 0, 0)
                updateCircularDrawableMatrixForLayerInset(animInsetLeft, animInsetTop)
            }
        }
        moveImage.addListener(object : AnimationListener() {
            override fun onAnimationStart(animation: Animator, isReverse: Boolean) {
                super.onAnimationStart(animation, isReverse)
                isMoveToStateAnimationPresent = true
            }

            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                isMoveToStateAnimationPresent = false
            }
        })
        moveImage.start()
        return moveImage
    }
}
