/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MessageEntity::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun dao(): MessageDao

    companion object {
        private lateinit var messageDatabase: MessageDatabase

        fun instance(context: Context): MessageDatabase {
            if (!Companion::messageDatabase.isInitialized) {
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
