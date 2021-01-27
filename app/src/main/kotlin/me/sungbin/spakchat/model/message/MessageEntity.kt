/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.model.message

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by SungBin on 2020-10-29.
 */

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
