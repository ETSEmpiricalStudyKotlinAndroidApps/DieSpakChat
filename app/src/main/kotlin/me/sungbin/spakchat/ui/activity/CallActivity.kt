/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.sfyc.ctpv.CountTimeProgressView
import me.sungbin.spakchat.databinding.ActivityCallBinding

class CallActivity : BaseActivity() {

    private var _binding: ActivityCallBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ctpvCounter.addOnEndListener(object : CountTimeProgressView.OnEndListener {
            override fun onAnimationEnd() {
                // todo: end call
            }

            override fun onClick(overageTime: Long) {
                // todo: open profile
            }
        })
        binding.ctpvCounter.startCountTimeAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
