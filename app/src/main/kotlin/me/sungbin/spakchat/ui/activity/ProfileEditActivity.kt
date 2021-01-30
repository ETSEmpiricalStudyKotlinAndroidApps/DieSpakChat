/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.r0adkll.slidr.Slidr
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.databinding.ActivityEditProfileBinding
import me.sungbin.spakchat.di.Firestore
import me.sungbin.spakchat.di.Storage
import javax.inject.Inject

@AndroidEntryPoint
class ProfileEditActivity : BaseActivity() {

    @Firestore
    @Inject
    lateinit var firestore: FirebaseFirestore

    @Storage
    @Inject
    lateinit var storage: StorageReference

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Slidr.attach(this)
    }
}
