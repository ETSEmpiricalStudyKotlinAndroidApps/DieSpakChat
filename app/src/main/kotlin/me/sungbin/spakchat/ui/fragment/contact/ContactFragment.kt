/*
 * Create by Sungbin Ji on 2021. 1. 29.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 */

package me.sungbin.spakchat.ui.fragment.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.FragmentContactBinding
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.ui.fragment.BaseFragment
import me.sungbin.spakchat.util.toArray

/**
 * Created by SungBin on 2020-09-10.
 */

class ContactFragment : BaseFragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_contact)
        }.show()

        val users = mutableListOf<User>()
        val dbThread = Thread {
            val usersEntity = userDb.dao().getAllUser()
            usersEntity.forEach {
                it.run {
                    val user = User(
                        key = key,
                        id = id,
                        email = email,
                        password = password,
                        name = name,
                        profileImage = profileImage?.toUri(),
                        profileImageColor = profileImageColor,
                        backgroundImage = backgroundImage?.toUri(),
                        statusMessage = statusMessage,
                        birthday = birthday,
                        lastOnline = lastOnline,
                        isOnline = isOnline,
                        friends = friends.toArray(),
                        sex = sex,
                        emoji = emoji.toArray(),
                        black = black.toArray(),
                        accountStatus = accountStatus,
                        isTestMode = isTestMode
                    )
                    users.add(user)
                }
            }
        }
        dbThread.start()
        dbThread.join() // todo: UI-Thread blocking /// .join() 말고 다르게 하기
        binding.rvFriends.adapter = FriendAdapter(users)
    }
}
