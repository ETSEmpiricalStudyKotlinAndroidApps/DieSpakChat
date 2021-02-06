/*
 * Create by Sungbin Ji on 2021. 1. 31.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.user.fragment.adapter

import androidx.recyclerview.widget.DiffUtil
import me.sungbin.spakchat.user.model.User

class FriendsDiffItemCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem.key == newItem.key
}
