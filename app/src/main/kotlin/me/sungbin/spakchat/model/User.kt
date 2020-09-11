package me.sungbin.spakchat.model

import java.util.*


/**
 * Created by SungBin on 2020-09-11.
 */

data class User(
    val uuid: String? = null,
    val id: String? = null,
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val profileImage: String? = null,
    val backgroundImage: String? = null,
    val statusMessage: String? = null,
    val birthday: Date? = null,
    val lastOnline: Date? = null,
    val isOnline: Boolean? = null,
    val friends: List<String>? = null, // for user-uuid
    val sex: Int? = null,
    val emoji: List<String>? = null, // for emoji-uuid
    val black: List<String>? = null, // for user-uuid
    val accountStatus: Int? = null,
)