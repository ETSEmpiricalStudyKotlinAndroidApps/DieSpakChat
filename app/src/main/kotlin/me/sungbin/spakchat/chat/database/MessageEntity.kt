/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MessageEntity(
    @PrimaryKey val key: Long? = null,
    val message: String? = null,
    val time: Long? = null, // Date().time
    val type: Int? = null,
    val attachment: Int? = null,
    val owner: Long? = null, // user-key
    val mention: String? = null, // for user-key // array
    val messageViewType: Int? = null
)
