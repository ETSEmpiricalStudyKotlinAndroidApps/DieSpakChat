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
import com.sungbin.androidutils.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.layout_signup.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.database.UserDatabase
import me.sungbin.spakchat.module.GlideApp
import me.sungbin.spakchat.ui.dialog.SigninBottomDialog
import me.sungbin.spakchat.ui.dialog.SignupBottomDialog
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by SungBin on 2020-09-11.
 */

@AndroidEntryPoint
class JoinActivity : BaseActivity() {

    private val signupBottomDialog by lazy { SignupBottomDialog.instance() } // 2중 싱글톤???
    private val signinBottomDialog by lazy { SigninBottomDialog.instance() } // 2중 싱글톤???

    @Inject
    @Named("user-db")
    lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        Thread {
            db.dao().getAllUser().map {
                Logger.w("${it.key}: ${it.name}")
            }
        }.start()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        btn_signup.setOnClickListener {
            signupBottomDialog.show(supportFragmentManager, "")
        }

        btn_signin.setOnClickListener {
            signinBottomDialog.show(supportFragmentManager, "")
        }

        tv_description.run {
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
                val uri =
                    data?.getParcelableArrayListExtra<Uri>(FishBun.INTENT_PATH)?.get(0) ?: return
                signupBottomDialog.iv_profile.setPadding(0, 0, 0, 0)
                GlideApp.with(applicationContext).load(uri).into(signupBottomDialog.iv_profile)
                signupBottomDialog.iv_profile.tag = uri
            }
        }
    }
}
