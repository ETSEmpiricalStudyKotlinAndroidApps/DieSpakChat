/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.room.model

data class Room(
    val key: Long,
    val name: String,
    val lastChatTime: Long,
    val lastChatPreview: String,
    val roomCoverImage: String,
)
