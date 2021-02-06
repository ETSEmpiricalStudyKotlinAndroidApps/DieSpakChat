/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import me.sungbin.spakchat.R
import me.sungbin.spakchat.SpakChat
import me.sungbin.spakchat.SpakViewModel
import me.sungbin.spakchat.chat.activity.ChatViewModel
import me.sungbin.spakchat.user.room.UserDatabase

abstract class BaseActivity : AppCompatActivity() {

    val globalVm = SpakViewModel.instance()
    val chatVm = ChatViewModel.instance()
    val userDb = UserDatabase.instance(SpakChat.context)
    val storage = Firebase.storage.reference
    val firestore = Firebase.firestore
    val database = Firebase.database.reference.apply { keepSynced(true) }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
