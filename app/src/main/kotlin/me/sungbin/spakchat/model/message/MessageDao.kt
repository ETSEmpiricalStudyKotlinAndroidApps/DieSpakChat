/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.model.message

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao { // todo: suspend fun 써보기
    @Query("SELECT * FROM MessageEntity")
    fun getAllUser(): List<MessageEntity>

//    @Query("SELECT * FROM MessageEntity WHERE key =:key")
//    fun getUserById(key: Long): MessageEntity? // 일치 항목 없을 땐 null 나옴

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg message: MessageEntity)

    @Delete
    fun delete(vararg message: MessageEntity)
}
