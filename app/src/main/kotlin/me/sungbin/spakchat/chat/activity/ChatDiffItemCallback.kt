/*
 * Create by Sungbin Ji on 2021. 2. 1.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.activity

import androidx.recyclerview.widget.DiffUtil
import me.sungbin.spakchat.chat.model.Chat

class ChatDiffItemCallback : DiffUtil.ItemCallback<Chat>() {

    override fun areItemsTheSame(oldItem: Chat, newItem: Chat) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Chat, newItem: Chat) = oldItem.key == newItem.key
}
