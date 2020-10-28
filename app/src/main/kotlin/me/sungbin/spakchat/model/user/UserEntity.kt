/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by SungBin on 2020-10-28.
 */

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
    val birthday: String?, // array
    val lastOnline: String?,
    val isOnline: Boolean?,
    val friends: String?, // for user-uuid // array
    val sex: Int?,
    val emoji: String?, // for emoji-uuid // array
    val black: String?, // for user-uuid // array
    val accountStatus: Int?,
    val isTestMode: Boolean?
)