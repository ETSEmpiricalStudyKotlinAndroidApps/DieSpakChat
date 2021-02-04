/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
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
    suspend fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE id =:id")
    suspend fun getUserById(id: String): UserEntity? // 일치 항목 없을 땐 null 나옴

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg user: UserEntity)

    @Delete
    suspend fun delete(vararg user: UserEntity)
}
