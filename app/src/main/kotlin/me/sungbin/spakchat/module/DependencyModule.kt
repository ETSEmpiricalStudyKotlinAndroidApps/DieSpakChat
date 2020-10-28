package me.sungbin.spakchat.module

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
    fun provideFirebaseStorage() = FirebaseStorage.getInstance().reference

    @Singleton
    @Named("firestore")
    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Named("database")
    @Provides
    fun provideFirebaseRealtimeDatabase() = FirebaseDatabase.getInstance().reference.apply {
        keepSynced(true)
    }

}