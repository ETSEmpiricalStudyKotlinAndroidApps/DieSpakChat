/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat

import androidx.lifecycle.ViewModel
import me.sungbin.spakchat.model.user.User

class SpakViewModel private constructor() : ViewModel() {

    lateinit var me: User
    val users = mutableListOf<User>()

    companion object {
        private val instance = SpakViewModel()
        fun instance() = instance
    }
}
