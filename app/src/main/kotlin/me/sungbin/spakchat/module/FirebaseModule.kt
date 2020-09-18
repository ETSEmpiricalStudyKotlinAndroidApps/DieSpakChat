package me.sungbin.spakchat.module

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by SungBin on 2020-09-18.
 */

@Module
@InstallIn(ApplicationComponent::class)
class FirebaseModule {

    @Provides
    @Named("firestore")
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Named("storage")
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorage.getInstance().reference

    @Provides
    @Named("database")
    @Singleton
    fun provideFirebaseRealtimeDatabase() = FirebaseDatabase.getInstance()

}