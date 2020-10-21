package me.sungbin.spakchat.ui.activity

import android.animation.Animator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import com.sungbin.androidutils.ui.TagableRoundImageView
import kotlinx.android.synthetic.main.activity_detailview_image.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.module.GlideApp
import me.sungbin.spakchat.ui.view.circleswipelayout.CircleSwipeLayout
import me.sungbin.spakchat.ui.view.circleswipelayout.transition.AnimationListener
import me.sungbin.spakchat.ui.view.circleswipelayout.transition.ChangeRoundedImageTransform
import me.sungbin.spakchat.ui.view.circleswipelayout.transition.TransitionListener

class DetailImageActivity : AppCompatActivity() {
    private var sharedElementEnterLeft: Int = 0
    private var sharedElementEnterTop: Int = 0
    private var sharedElementEnterRadius: Int = 0
    private var isInformationUiHidden = false
    private var isFlingSwiped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViews(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        initializeTransitionCallback()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        initializeSystemUi()
    }

    private fun initializeViews(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detailview_image)

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
        GlideApp.with(applicationContext).load(colorDrawable).into(iv_photo)
        GlideApp.with(applicationContext).load(colorDrawable).into(iv_profile)

        tv_name.text = intent.getStringExtra("name")

        btn_back.setOnClickListener {
            supportFinishAfterTransition()
        }

        swipeLayout.listener = object : CircleSwipeLayout.Listener {
            override fun onSwipeStarted() {
            }

            override fun onSwipeCancelled() {
            }

            override fun onSwiped() {
                swipeLayout.autoReset = false
                startExitAnimation()
            }

            override fun onSwipedFling() {
                isFlingSwiped = true
            }

            override fun onSwipeReset() {
                if (isFlingSwiped)
                    supportFinishAfterTransition()
            }
        }

        swipeLayout.setOnClickListener {
            if (!swipeLayout.isTransitionPresent()) {
                if (!isInformationUiHidden) {
                    supportFinishAfterTransition()
                } else {
                    swipeLayout.swipeEnabled = true
                    isInformationUiHidden = false
                }
            }
        }

        swipeLayout.setOnLongClickListener {
            if (!swipeLayout.isTransitionPresent()) {
                swipeLayout.swipeEnabled = false
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
                sharedElementSnapshots: List<View>
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

            override fun onTransitionEnd(transition: Transition?) {
            }
        })
    }

    private fun initializeSystemUi() {
        @Suppress("DEPRECATION")
        window!!.decorView.systemUiVisibility += (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun showInformationUi(): ViewPropertyAnimator {
        return layoutInformation.animate().alpha(1f)

    }

    private fun hideInformationUi(): ViewPropertyAnimator {
        return layoutInformation.animate().alpha(0f)
    }

    private fun startExitAnimation() {
        swipeLayout.moveCircleToState(
            sharedElementEnterLeft,
            sharedElementEnterTop,
            sharedElementEnterRadius,
            0
        )?.addListener(object : AnimationListener() {
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)

                if (!isFinishing)
                    finish()
            }
        })
    }
}