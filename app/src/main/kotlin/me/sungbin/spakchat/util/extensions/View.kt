/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util.extensions

import android.animation.Animator
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import me.sungbin.androidutils.extensions.hide
import me.sungbin.androidutils.extensions.show

fun View.showWithAnimate() {
    show()
    YoYo
        .with(Techniques.FadeIn)
        .duration(250)
        .playOn(this)
}

fun View.hideWithAnimate(isGone: Boolean = false) {
    YoYo
        .with(Techniques.FadeOut)
        .duration(250)
        .withListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                hide(isGone)
            }
        })
        .playOn(this)
}

// todo: 이 코드 이해하기
// usage: private val binding by viewBindings(ActivityMainBinding::inflate)
// inline fun <reified VB : ViewBinding> Activity.viewBindings(
//    crossinline inflater: (LayoutInflater) -> VB,
// ): Lazy<VB> {
//    return lazy(LazyThreadSafetyMode.NONE) {
//        inflater(layoutInflater)
//    }
// }
