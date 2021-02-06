/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity

import android.animation.Animator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import androidx.core.app.SharedElementCallback
import me.sungbin.androidutils.tagableroundimageview.TagableRoundImageView
import me.sungbin.spakchat.GlideApp
import me.sungbin.spakchat.databinding.ActivityDetailviewImageBinding
import me.sungbin.spakchat.ui.view.circleswipelayout.CircleSwipeListener
import me.sungbin.spakchat.ui.view.circleswipelayout.transition.AnimationListener
import me.sungbin.spakchat.ui.view.circleswipelayout.transition.ChangeRoundedImageTransform
import me.sungbin.spakchat.ui.view.circleswipelayout.transition.TransitionListener

class DetailImageActivity : BaseActivity() {

    private var sharedElementEnterLeft = 0
    private var sharedElementEnterTop = 0
    private var sharedElementEnterRadius = 0
    private var isInformationUiHidden = false
    private var isFlingSwiped = false

    private var _binding: ActivityDetailviewImageBinding? = null
    private val binding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailviewImageBinding.inflate(layoutInflater)
        initializeBinding()
    }

    override fun onStart() {
        super.onStart()
        initializeTransitionCallback()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initializeSystemUi()
    }

    private fun initializeBinding() {
        setContentView(binding.root)

        // todo: 이미지 불러오는 부분
        /*val path = ImageUtils.getDownloadFilePath(intent.getStringExtra("image")!!)
        when {
            path.contains("mp4") -> {
                image.visibility = View.GONE
                videoView.visibility = View.VISIBLE

                videoView.setVideoURI(Uri.parse(intent.getStringExtra("image")!!))
                videoView.start()
            }
            else -> ImageUtils.set(path, image, applicationContext)
        }
        Glide.set(applicationContext, intent.getStringExtra("avatar")!!, imageViewUser)
        name.text = intent.getStringExtra("name")*/

        val colorDrawable = ColorDrawable(intent.getIntExtra("image", 0))
        GlideApp.with(applicationContext).load(colorDrawable).into(binding.ivPhoto)
        GlideApp.with(applicationContext).load(colorDrawable).into(binding.ivProfile)

        binding.tvName.text = intent.getStringExtra("name")

        binding.btnBack.setOnClickListener {
            supportFinishAfterTransition()
        }

        binding.swipeLayout.circleSwipeListener = object : CircleSwipeListener {
            override fun onSwipeStarted() {}
            override fun onSwipeCancelled() {}

            override fun onSwiped() {
                binding.swipeLayout.autoReset = false
                startExitAnimation()
            }

            override fun onSwipedFling() {
                isFlingSwiped = true
            }

            override fun onSwipeReset() {
                if (isFlingSwiped) {
                    supportFinishAfterTransition()
                }
            }
        }

        binding.swipeLayout.setOnClickListener {
            if (!binding.swipeLayout.isTransitionPresent()) {
                if (!isInformationUiHidden) {
                    supportFinishAfterTransition()
                } else {
                    binding.swipeLayout.swipeEnabled = true
                    isInformationUiHidden = false
                }
            }
        }

        binding.swipeLayout.setOnLongClickListener {
            if (!binding.swipeLayout.isTransitionPresent()) {
                binding.swipeLayout.swipeEnabled = false
                isInformationUiHidden = true
                hideInformationUi()
            }
            false
        }
    }

    private fun initializeTransitionCallback() {
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(
                sharedElementNames: List<String>,
                sharedElements: List<View>,
                sharedElementSnapshots: List<View>,
            ) {
                super.onSharedElementStart(
                    sharedElementNames,
                    sharedElements,
                    sharedElementSnapshots
                )

                for (view in sharedElements) {
                    if (view is TagableRoundImageView) {
                        sharedElementEnterLeft = view.left
                        sharedElementEnterTop = view.top
                        break
                    }
                }
            }
        })

        val sharedElementTransition = window.sharedElementEnterTransition
        sharedElementTransition?.addListener(object : TransitionListener() {
            override fun onTransitionStart(transition: Transition?) {
                super.onTransitionStart(transition)

                val roundedImageTransform: ChangeRoundedImageTransform? =
                    if (transition is TransitionSet) {
                        var roundedImageTransform: ChangeRoundedImageTransform? = null
                        for (i in 0..transition.transitionCount) {
                            val newTransition = transition.getTransitionAt(i)
                            if (newTransition is ChangeRoundedImageTransform) {
                                roundedImageTransform = newTransition
                                break
                            }
                        }
                        roundedImageTransform
                    } else if (transition is ChangeRoundedImageTransform) {
                        transition
                    } else {
                        null
                    }
                sharedElementEnterRadius =
                    roundedImageTransform?.getFromRadius() ?: sharedElementEnterRadius
            }

            override fun onTransitionEnd(transition: Transition?) {}
        })
    }

    private fun initializeSystemUi() {
        @Suppress("DEPRECATION")
        window!!.decorView.systemUiVisibility += (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            )
    }

    private fun showInformationUi() = binding.clContainerToolbar.animate().alpha(1f)
    private fun hideInformationUi() = binding.clContainerToolbar.animate().alpha(0f)

    private fun startExitAnimation() {
        binding.swipeLayout.moveCircleToState(
            sharedElementEnterLeft,
            sharedElementEnterTop,
            sharedElementEnterRadius,
            0
        )?.addListener(object : AnimationListener() {
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                if (!isFinishing) {
                    finish()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
