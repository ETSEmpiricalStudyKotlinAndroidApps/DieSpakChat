/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageEntity")
    suspend fun getAllMessage(): List<MessageEntity>

    @Query("SELECT * FROM MessageEntity WHERE `key` =:key")
    fun getMessageByKey(key: Long): MessageEntity? // 일치 항목 없을 땐 null 나옴

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg message: MessageEntity)

    @Delete
    suspend fun delete(vararg message: MessageEntity)
}
