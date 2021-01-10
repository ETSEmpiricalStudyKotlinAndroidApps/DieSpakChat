package me.sungbin.spakchat.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.WindowManager
import com.sangcomz.fishbun.FishBun
import com.sungbin.androidutils.ui.TagableRoundImageView
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.ActivityJoinBinding
import me.sungbin.spakchat.module.GlideApp
import me.sungbin.spakchat.ui.dialog.SigninBottomDialog
import me.sungbin.spakchat.ui.dialog.SignupBottomDialog

/**
 * Created by SungBin on 2020-09-11.
 */

@AndroidEntryPoint
class JoinActivity : BaseActivity() {

    private val signupBottomDialog by lazy { SignupBottomDialog.instance() }
    private val signinBottomDialog by lazy { SigninBottomDialog.instance() }

    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        binding.btnSignup.setOnClickListener {
            signupBottomDialog.show(supportFragmentManager, "")
        }

        binding.btnSignin.setOnClickListener {
            signinBottomDialog.show(supportFragmentManager, "")
        }

        binding.tvDescription.run {
            val ssb = SpannableStringBuilder(text)
            ssb.setSpan(
                StyleSpan(Typeface.BOLD),
                text.lastIndexOf("새로운"),
                text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ssb.setSpan(
                RelativeSizeSpan(1.6f),
                text.lastIndexOf("새로운"),
                text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = ssb
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FishBun.FISHBUN_REQUEST_CODE -> {
                val profileImageView =
                    signupBottomDialog.view?.findViewById<TagableRoundImageView>(R.id.iv_profile)
                val uri =
                    data?.getParcelableArrayListExtra<Uri>(FishBun.INTENT_PATH)?.get(0) ?: return
                GlideApp.with(applicationContext).load(uri).into(profileImageView!!)
                profileImageView.tag = uri
            }
        }
    }
}
