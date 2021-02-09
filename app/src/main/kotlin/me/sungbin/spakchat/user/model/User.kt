/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.user.model

import me.sungbin.spakchat.room.Room

data class User(
    val key: Long? = null,
    val userId: String? = null,
    val loginId: String? = null,
    val password: String? = null,
    var name: String? = null,
    var profileImage: String? = null,
    var profileImageColor: Int? = null,
    var backgroundImage: String? = null,
    var statusMessage: String? = null,
    var birthday: Long? = null, // Date().time
    var lastOnline: Long? = null, // Date().time
    var isOnline: Boolean? = null,
    val rooms: MutableList<Long>? = null, // for room-key
    val friends: MutableList<Long>? = null, // for user-key
    val sex: Int? = null,
    val emoji: MutableList<Long>? = null, // for emoji-key
    val black: MutableList<Long>? = null, // for user-key
    val accountStatus: Int? = null,
) {

    fun toRoom() = Room(
        key = key!!,
        name = name!!,
        roomCoverImage = profileImage ?: profileImageColor.toString()
    )
}
