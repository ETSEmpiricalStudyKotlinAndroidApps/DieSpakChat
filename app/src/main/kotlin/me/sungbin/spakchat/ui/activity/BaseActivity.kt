package me.sungbin.spakchat.ui.activity

import androidx.appcompat.app.AppCompatActivity
import me.sungbin.spakchat.R

/**
 * Created by SungBin on 2020-10-21.
 */


abstract class BaseActivity : AppCompatActivity() {

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}