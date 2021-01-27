package me.sungbin.spakchat.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.ScrollingMovementMethod
import android.text.style.StyleSpan
import me.sungbin.spakchat.databinding.ActivityExceptionBinding

class ExceptionActivity : BaseActivity() {

    private val binding by lazy { ActivityExceptionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}
