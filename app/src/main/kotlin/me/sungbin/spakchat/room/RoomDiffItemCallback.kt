/*
 * Create by Sungbin Ji on 2021. 2. 9.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.room

import androidx.recyclerview.widget.DiffUtil

class RoomDiffItemCallback : DiffUtil.ItemCallback<Room>() {

    override fun areItemsTheSame(oldItem: Room, newItem: Room) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Room, newItem: Room) = oldItem.key == newItem.key
}
