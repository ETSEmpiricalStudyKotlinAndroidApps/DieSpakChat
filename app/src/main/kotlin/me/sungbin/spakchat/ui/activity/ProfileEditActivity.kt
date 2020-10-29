/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.r0adkll.slidr.Slidr
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.R
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by SungBin on 2020-10-29.
 */

@AndroidEntryPoint
class ProfileEditActivity : BaseActivity() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
    lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        Slidr.attach(this)

    }
}