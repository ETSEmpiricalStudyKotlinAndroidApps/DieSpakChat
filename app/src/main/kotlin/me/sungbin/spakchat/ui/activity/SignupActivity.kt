package me.sungbin.spakchat.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.ui.dialog.SigninBottomDialog
import me.sungbin.spakchat.ui.dialog.SignupBottomDialog


/**
 * Created by SungBin on 2020-09-11.
 */

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        btn_signup.setOnClickListener {
            val bottomSheetDialog = SignupBottomDialog.instance()
            bottomSheetDialog.show(supportFragmentManager, "")
        }

        btn_signin.setOnClickListener {
            val bottomSheetDialog = SigninBottomDialog.instance()
            bottomSheetDialog.show(supportFragmentManager, "")
        }

        tv_description.run {
            val ssb = SpannableStringBuilder(text)
            ssb.setSpan(
                StyleSpan(Typeface.BOLD),
                text.lastIndexOf("새로운"),
                text.lastIndex+1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ssb.setSpan(
                RelativeSizeSpan(1.6f),
                text.lastIndexOf("새로운"),
                text.lastIndex+1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = ssb
        }
    }

}