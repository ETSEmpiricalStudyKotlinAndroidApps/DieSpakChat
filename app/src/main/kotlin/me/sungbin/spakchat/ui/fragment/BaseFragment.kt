/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject
import me.sungbin.spakchat.SpakViewModel
import me.sungbin.spakchat.database.UserDatabase
import me.sungbin.spakchat.di.Firestore
import me.sungbin.spakchat.di.RTDB
import me.sungbin.spakchat.di.Storage
import me.sungbin.spakchat.di.UserDB

@AndroidEntryPoint
@WithFragmentBindings
abstract class BaseFragment : Fragment() {

    @Firestore
    @Inject
    lateinit var firestore: FirebaseFirestore

    @Storage
    @Inject
    lateinit var storage: StorageReference

    @RTDB
    @Inject
    lateinit var database: DatabaseReference

    @UserDB
    @Inject
    lateinit var userDb: UserDatabase

    val vm = SpakViewModel.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = false
    }
}
