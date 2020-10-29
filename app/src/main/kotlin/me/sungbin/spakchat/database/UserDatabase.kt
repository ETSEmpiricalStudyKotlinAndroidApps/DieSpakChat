/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.sungbin.spakchat.model.user.UserDao
import me.sungbin.spakchat.model.user.UserEntity

/**
 * Created by SungBin on 2020-10-28.
 */

@Database(entities = [UserEntity::class], version = 4)
abstract class UserDatabase : RoomDatabase() {
    abstract fun dao(): UserDao

    companion object {
        private lateinit var userDatabase: UserDatabase

        fun instance(context: Context): UserDatabase {
            if (!::userDatabase.isInitialized) {
                synchronized(UserDatabase::class) {
                    userDatabase = Room.databaseBuilder(
                        context,
                        UserDatabase::class.java, "user.db"
                    ).fallbackToDestructiveMigration() // for Migration (database version change)
                        .build()
                }
            }
            return userDatabase
        }
    }
}