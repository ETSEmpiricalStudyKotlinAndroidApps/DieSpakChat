/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.sungbin.spakchat.model.message.MessageDao
import me.sungbin.spakchat.model.message.MessageEntity

/**
 * Created by SungBin on 2020-10-29.
 */

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