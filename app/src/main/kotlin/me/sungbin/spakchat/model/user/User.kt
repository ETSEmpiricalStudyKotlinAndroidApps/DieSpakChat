/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.model.user

data class User(
    val key: Long? = null,
    val id: String? = null,
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val profileImage: String? = null,
    val profileImageColor: Int? = null,
    val backgroundImage: String? = null,
    val statusMessage: String? = null,
    val birthday: Long? = null, // Date().time
    val lastOnline: Long? = null, // Date().time
    val isOnline: Boolean? = null,
    val friends: List<Long>? = null, // for user-key
    val sex: Int? = null,
    val emoji: List<Long>? = null, // for emoji-key
    val black: List<Long>? = null, // for user-key
    val accountStatus: Int? = null,
    val isTestMode: Boolean? = null,
)
