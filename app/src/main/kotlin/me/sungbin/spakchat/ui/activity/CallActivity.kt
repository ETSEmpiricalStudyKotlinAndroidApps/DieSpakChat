package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.sfyc.ctpv.CountTimeProgressView
import kotlinx.android.synthetic.main.activity_call.*
import me.sungbin.spakchat.R

/**
 * Created by SungBin on 2020-09-19.
 */

class CallActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        ctpv_counter.addOnEndListener(object : CountTimeProgressView.OnEndListener {
            override fun onAnimationEnd() {
                // todo: 통화 끊기
            }

            override fun onClick(overageTime: Long) {
                // todo: 프로필 열기
            }
        })
        ctpv_counter.startCountTimeAnimation()
    }

}