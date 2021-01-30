/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserEntity(
    @PrimaryKey val key: Long?,
    val id: String?,
    val email: String?,
    val password: String?,
    val name: String?,
    val profileImage: String?,
    val profileImageColor: Int?,
    val backgroundImage: String?,
    val statusMessage: String?,
    val birthday: Long?, // Date().time
    val lastOnline: Long?, // Date().time
    val isOnline: Boolean?,
    val friends: String?, // for user-key // array
    val sex: Int?,
    val emoji: String?, // for emoji-key // array
    val black: String?, // for user-key // array
    val accountStatus: Int?,
    val isTestMode: Boolean?
)
