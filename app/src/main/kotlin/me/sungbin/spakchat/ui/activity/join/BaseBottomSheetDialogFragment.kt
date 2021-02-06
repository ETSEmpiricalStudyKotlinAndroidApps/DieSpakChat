/*
 * Create by Sungbin Ji on 2021. 2. 5.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.join

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import me.sungbin.spakchat.SpakChat
import me.sungbin.spakchat.user.UserViewModel
import me.sungbin.spakchat.chat.ChatViewModel
import me.sungbin.spakchat.user.database.UserDatabase

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    val globalVm = UserViewModel.instance()
    val chatVm = ChatViewModel.instance()
    val userDb = UserDatabase.instance(SpakChat.context)
    val storage = Firebase.storage.reference
    val firestore = Firebase.firestore
    val rtdb = Firebase.database.reference.apply {
        keepSynced(true)
    }
}
