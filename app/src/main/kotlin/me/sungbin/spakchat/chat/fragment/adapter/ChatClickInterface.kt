/*
 * Create by Sungbin Ji on 2021. 2. 7.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.fragment.adapter

import me.sungbin.spakchat.chat.model.Chat

interface ChatClickInterface {

    fun onChatClicked(chat: Chat)
}
