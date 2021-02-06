/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.user.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.sungbin.spakchat.annotation.ApplicationContext

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun dao(): UserDao

    companion object {
        private lateinit var userDatabase: UserDatabase

        fun instance(@ApplicationContext context: Context): UserDatabase {
            if (!Companion::userDatabase.isInitialized) {
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
