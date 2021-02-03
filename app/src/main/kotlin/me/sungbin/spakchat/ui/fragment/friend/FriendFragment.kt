/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.fragment.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.FragmentFriendBinding
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.ui.fragment.BaseFragment
import me.sungbin.spakchat.ui.fragment.friend.adapter.FriendListAdapter
import me.sungbin.spakchat.util.ArrayConverter.toArray

/**
 * Created by SungBin on 2020-09-10.
 */

class FriendFragment : BaseFragment() {

    private var _binding: FragmentFriendBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFriendBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_contact)
        }.show()

        val friendAdapter = FriendListAdapter()
        binding.rvFriends.adapter = friendAdapter
        friendAdapter.submit(vm.users)

        if (vm.users.isEmpty()) {
            Thread {
                val usersEntity = userDb.dao().getAllUser()
                usersEntity.forEach {
                    with(it) {
                        val user = User(
                            key = key,
                            userId = id,
                            loginId = email,
                            password = password,
                            name = name,
                            profileImage = profileImage,
                            profileImageColor = profileImageColor,
                            backgroundImage = backgroundImage,
                            statusMessage = statusMessage,
                            birthday = birthday,
                            lastOnline = lastOnline,
                            isOnline = isOnline,
                            friends = toArray<Long>(friends),
                            sex = sex,
                            emoji = toArray<Long>(emoji),
                            black = toArray<Long>(black),
                            accountStatus = accountStatus,
                            isTestMode = isTestMode
                        )
                        vm.users.add(user)
                        friendAdapter.submit(vm.users)
                    }
                }
            }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
