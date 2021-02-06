/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.user

import androidx.lifecycle.ViewModel
import me.sungbin.spakchat.user.model.User

class UserViewModel private constructor() : ViewModel() {

    lateinit var me: User
    val users = mutableListOf<User>()

    companion object {
        private val instance = UserViewModel()
        fun instance() = instance
    }
}
