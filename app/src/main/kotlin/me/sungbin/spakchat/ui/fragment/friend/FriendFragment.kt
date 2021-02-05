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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sungbin.androidutils.extensions.startActivity
import me.sungbin.androidutils.util.Logger
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.FragmentFriendBinding
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.ui.activity.chat.ChatActivity
import me.sungbin.spakchat.ui.fragment.BaseFragment
import me.sungbin.spakchat.ui.fragment.friend.adapter.FriendListAdapter
import me.sungbin.spakchat.util.ArrayConverter.toArray
import me.sungbin.spakchat.util.KeyManager

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
        friendAdapter.setOnFriendClickListener {
            startActivity<ChatActivity>(false, KeyManager.User.KEY to key)
        }
        binding.rvFriends.adapter = friendAdapter
        friendAdapter.submit(globalVm.users)

        if (globalVm.users.isEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) { // todo: Is this the best way to use coroutines?
                val usersEntity = userDb.dao().getAllUser()
                usersEntity.forEach {
                    with(it) {
                        if (key == globalVm.me.key) return@with
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
                        globalVm.users.add(user)
                        friendAdapter.submit(globalVm.users)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Logger.i("Destroy `FriendFragment`")
    }
}
