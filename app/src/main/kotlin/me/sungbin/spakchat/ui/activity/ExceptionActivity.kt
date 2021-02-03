/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.ScrollingMovementMethod
import android.text.style.StyleSpan
import me.sungbin.spakchat.databinding.ActivityExceptionBinding

class ExceptionActivity : BaseActivity() {

    private var _binding: ActivityExceptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityExceptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra("message") ?: "NullPointerException"
        binding.tvException.run {
            val ssb = SpannableStringBuilder(message)
            ssb.setSpan(
                StyleSpan(Typeface.ITALIC),
                message.lastIndexOf("#"),
                message.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = ssb
            movementMethod = ScrollingMovementMethod()
        }

        binding.lavException.setOnClickListener {
            binding.lavException.playAnimation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
