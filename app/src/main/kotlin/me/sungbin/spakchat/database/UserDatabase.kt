/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.sungbin.spakchat.model.user.UserDao
import me.sungbin.spakchat.model.user.UserEntity

@Database(entities = [UserEntity::class], version = 1)
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
