package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.sfyc.ctpv.CountTimeProgressView
import me.sungbin.spakchat.databinding.ActivityCallBinding

/**
 * Created by SungBin on 2020-09-19.
 */

class CallActivity : BaseActivity() {

    private val binding by lazy { ActivityCallBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ctpvCounter.addOnEndListener(object : CountTimeProgressView.OnEndListener {
            override fun onAnimationEnd() {
                // todo: 통화 끊기
            }

            override fun onClick(overageTime: Long) {
                // todo: 프로필 열기
            }
        })
        binding.ctpvCounter.startCountTimeAnimation()
    }

}