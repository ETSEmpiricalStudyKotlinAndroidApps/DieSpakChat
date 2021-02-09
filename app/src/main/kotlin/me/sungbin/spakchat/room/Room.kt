/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.room

data class Room(
    val key: Long? = null,
    val name: String? = null,
    val unreadCount: Int? = null,
    val lastMessage: String? = null,
    val lastChatTime: String? = null,
    val joinCode: String? = null,
    val roomCoverImage: String? = null,
    val kick: MutableList<Long>? = null, // todo
)
