/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.sungbin.spakchat.model.message.MessageDao
import me.sungbin.spakchat.model.message.MessageEntity

@Database(entities = [MessageEntity::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun dao(): MessageDao

    companion object {
        private lateinit var messageDatabase: MessageDatabase

        fun instance(context: Context): MessageDatabase {
            if (!::messageDatabase.isInitialized) {
                synchronized(MessageDatabase::class) {
                    messageDatabase = Room.databaseBuilder(
                        context,
                        MessageDatabase::class.java, "message.db"
                    ).fallbackToDestructiveMigration() // for Migration (database version change)
                        .build()
                }
            }
            return messageDatabase
        }
    }
}
