/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.sfyc.ctpv.CountTimeProgressView
import me.sungbin.spakchat.databinding.ActivityCallBinding

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
