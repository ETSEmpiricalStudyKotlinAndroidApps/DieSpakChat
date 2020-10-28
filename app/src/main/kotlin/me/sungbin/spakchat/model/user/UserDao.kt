/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.model.user

import androidx.room.*

/**
 * Created by SungBin on 2020-10-28.
 */

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT name FROM UserEntity WHERE id =:id")
    fun getUserById(id: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user: UserEntity)

    @Delete
    fun delete(vararg user: UserEntity)
}