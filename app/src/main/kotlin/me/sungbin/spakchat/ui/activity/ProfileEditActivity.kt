/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.r0adkll.slidr.Slidr
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.databinding.ActivityEditProfileBinding
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

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Slidr.attach(this)
    }
}