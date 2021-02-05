/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.model.message

import me.sungbin.spakchat.model.user.User

data class Message(
    val key: Long? = null,
    val message: String? = null,
    val time: Long? = null, // Date().time
    val type: Int? = null,
    val attachment: Int? = null,
    val owner: User? = null,
    val mention: List<Long>? = null, // for user-key
    val messageViewType: Int? = null,
) {
    override fun hashCode() = key!!.toInt()
    override fun equals(other: Any?) = with(other as Message) {
        key == this.key
    }
}
