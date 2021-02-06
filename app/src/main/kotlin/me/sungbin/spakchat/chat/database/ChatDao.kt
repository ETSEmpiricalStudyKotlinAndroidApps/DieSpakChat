/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {

    @Query("SELECT * FROM ChatEntity")
    suspend fun getAllMessage(): List<ChatEntity>

    @Query("SELECT * FROM ChatEntity WHERE `key` =:key")
    suspend fun getMessageByKey(key: Long): ChatEntity? // 일치 항목 없을 땐 null 나옴

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg chat: ChatEntity)

    @Delete
    suspend fun delete(vararg chat: ChatEntity)
}
