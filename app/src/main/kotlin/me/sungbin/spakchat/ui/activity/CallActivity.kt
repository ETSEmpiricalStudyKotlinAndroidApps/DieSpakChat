package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sfyc.ctpv.CountTimeProgressView
import com.sungbin.sungbintool.util.Logger
import kotlinx.android.synthetic.main.activity_call.*
import me.sungbin.spakchat.R


/**
 * Created by SungBin on 2020-09-19.
 */

class CallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        with (ctpv_counter) {
            addOnEndListener(object : CountTimeProgressView.OnEndListener {
                override fun onAnimationEnd() {
                    Logger.w("AA")
                }

                override fun onClick(overageTime: Long) {
                    Logger.w("AABB")
                }
            })
            startCountTimeAnimation()
        }
    }

}