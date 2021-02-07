/*
 * Create by Sungbin Ji on 2021. 2. 4.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.user.fragment.adapter

import me.sungbin.spakchat.user.model.User

interface FriendClickListener {

    fun onFriendClick(friend: User)
}
