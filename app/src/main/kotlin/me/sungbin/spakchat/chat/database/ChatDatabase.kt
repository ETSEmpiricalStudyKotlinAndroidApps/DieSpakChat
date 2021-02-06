/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ChatEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun dao(): ChatDao

    companion object {
        private lateinit var chatDatabase: ChatDatabase

        fun instance(context: Context): ChatDatabase {
            if (!Companion::chatDatabase.isInitialized) {
                synchronized(ChatDatabase::class) {
                    chatDatabase = Room.databaseBuilder(
                        context,
                        ChatDatabase::class.java, "message.db"
                    ).fallbackToDestructiveMigration() // for Migration (database version change)
                        .build()
                }
            }
            return chatDatabase
        }
    }
}
