package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import me.sungbin.spakchat.R
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
    }

}