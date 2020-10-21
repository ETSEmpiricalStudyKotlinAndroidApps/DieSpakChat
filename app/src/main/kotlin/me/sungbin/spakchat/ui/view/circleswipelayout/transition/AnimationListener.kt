package me.sungbin.spakchat.ui.view.circleswipelayout.transition

import android.animation.Animator

abstract class AnimationListener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator, isReverse: Boolean) {
    }

    override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
    }

    override fun onAnimationStart(animator: Animator) {
    }

    override fun onAnimationEnd(animator: Animator) {
    }

    override fun onAnimationCancel(animator: Animator) {
    }

    override fun onAnimationRepeat(animator: Animator) {
    }
}