/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat

import android.widget.TextView
import androidx.databinding.BindingAdapter
import me.sungbin.spakchat.chat.model.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

object ChatBinding {

    @JvmStatic
    @BindingAdapter("spak_loadMessage")
    fun loadMessage(textView: TextView, message: Message) {
        // todo: 메시지 타입 가져와서 처리하기
        textView.text = message.message ?: "unknown message"
    }

    @JvmStatic
    @BindingAdapter("spak_loadDate")
    fun loadDate(textView: TextView, time: Long) {
        val formatter = SimpleDateFormat("aa hh:mm", Locale.KOREA)
        val date = Date()
        date.time = time
        textView.text = formatter.format(date)
    }

    @JvmStatic
    @BindingAdapter("spak_loadUnreadCount")
    fun loadUnreadCount(textView: TextView, message: Message) {
        // todo
        textView.text = Random.nextInt(0, 100).toString()
    }
}
