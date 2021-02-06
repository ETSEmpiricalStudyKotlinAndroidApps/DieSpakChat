/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.view.circleswipelayout.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.ViewGroup
import me.sungbin.androidutils.tagableroundimageview.TagableRoundImageView
import me.sungbin.spakchat.R

// https://github.com/salih-demir/swipe-layout
class ChangeRoundedImageTransform(context: Context?, attrs: AttributeSet?) :
    ChangeBounds(context, attrs) {

    private var fromRadius: Int = 0
    private var toRadius: Int = 0

    init {
        if (context != null && attrs != null) {
            val ta = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.RoundedImageTransition,
                0,
                0
            )
            fromRadius =
                ta.getDimensionPixelSize(R.styleable.RoundedImageTransition_fromRadius, fromRadius)
            toRadius =
                ta.getDimensionPixelSize(R.styleable.RoundedImageTransition_toRadius, toRadius)
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues,
        endValues: TransitionValues,
    ): Animator {
        val changeBounds = super.createAnimator(sceneRoot, startValues, endValues)

        val changeRadius = ValueAnimator.ofInt(fromRadius, toRadius)
        changeRadius.addUpdateListener { animator ->
            val view = startValues.view
            if (view is TagableRoundImageView) {
                view.updateRadius(animator.animatedValue as Int)
            }
        }

        return AnimatorSet().apply {
            playTogether(changeBounds, changeRadius)
        }
    }

    fun getFromRadius() = fromRadius
    fun getToRadius() = toRadius
}
