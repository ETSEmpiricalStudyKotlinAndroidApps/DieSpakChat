package me.sungbin.spakchat.module

import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.sungbin.spakchat.SpakChat
import me.sungbin.spakchat.database.UserDatabase
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by SungBin on 2020-09-18.
 */

@Module
@InstallIn(ApplicationComponent::class)
class DependencyModule {

    @Singleton
    @Named("user-db")
    @Provides
    fun provideUserRoom() = UserDatabase.instance(SpakChat.context)

    @Singleton
    @Named("storage")
    @Provides
    fun provideFirebaseStorage() = Firebase.storage.reference

    @Singleton
    @Named("firestore")
    @Provides
    fun provideFirestore() = Firebase.firestore

    @Singleton
    @Named("database")
    @Provides
    fun provideFirebaseRealtimeDatabase() = Firebase.database.reference.apply {
        keepSynced(true)
    }

}