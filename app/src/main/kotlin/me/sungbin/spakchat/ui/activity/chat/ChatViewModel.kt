/*
 * Create by Sungbin Ji on 2021. 2. 1.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.sungbin.spakchat.model.message.Message

class ChatViewModel private constructor() : ViewModel() {

    val messagesMap = HashMap<Long, MutableList<Message>>()
    val message: MutableLiveData<Message> = MutableLiveData()

    companion object {
        private val instance = ChatViewModel()
        fun instance() = instance
    }
}
