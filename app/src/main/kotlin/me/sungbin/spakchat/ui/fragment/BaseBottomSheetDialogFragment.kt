/*
 * Create by Sungbin Ji on 2021. 2. 7.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.fragment

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import me.sungbin.spakchat.user.UserViewModel

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    val userVm = UserViewModel.instance()
    val storage = Firebase.storage.reference
    val database = Firebase.database.reference.apply { keepSynced(true) }
    val firestore = Firebase.firestore
}
