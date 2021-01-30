/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.di

import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.sungbin.spakchat.SpakChat
import me.sungbin.spakchat.database.UserDatabase

@Module
@InstallIn(ApplicationComponent::class)
class Module {

    @UserDB
    @Reusable
    @Provides
    fun provideUserRoom() = UserDatabase.instance(SpakChat.context)

    @Storage
    @Reusable
    @Provides
    fun provideFirebaseStorage() = Firebase.storage.reference

    @Firestore
    @Reusable
    @Provides
    fun provideFirestore() = Firebase.firestore

    @RealtimeDatabase
    @Reusable
    @Provides
    fun provideRealtimeDatabase() = Firebase.database.reference.apply {
        keepSynced(true)
    }
}
