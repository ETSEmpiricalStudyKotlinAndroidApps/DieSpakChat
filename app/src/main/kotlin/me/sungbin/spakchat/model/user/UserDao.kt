/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.model.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao { // todo: suspend fun 써보기
    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE id =:id")
    fun getUserById(id: String): UserEntity? // 일치 항목 없을 땐 null 나옴

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user: UserEntity)

    @Delete
    fun delete(vararg user: UserEntity)
}
