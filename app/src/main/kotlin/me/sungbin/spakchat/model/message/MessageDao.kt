/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.model.message

import androidx.room.*

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