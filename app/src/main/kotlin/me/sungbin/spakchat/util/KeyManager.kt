/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

object KeyManager {

    object ChatType {

        fun toKey() = "ChatType"
        const val FRIENDS = "friends"
        const val OPEN = "open"
    }

    object Room {

        const val KEY = "ROOM_KEY"
    }

    object User {

        const val KEY = "USER_KEY"
        const val ID = "USER_ID"
        const val PASSWORD = "USER_PASSWORD"
    }
}
