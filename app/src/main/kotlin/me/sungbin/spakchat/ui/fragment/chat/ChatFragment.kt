/*
 * Create by Sungbin Ji on 2021. 1. 29.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 */

package me.sungbin.spakchat.ui.fragment.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.sungbin.androidutils.extensions.setFab
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.FragmentChatBinding
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.ui.fragment.BaseFragment

/**
 * Created by SungBin on 2020-09-10.
 */

class ChatFragment : BaseFragment() {

    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_chat)
        }.show()

//        rv_online.adapter = OnlineAdapter(db.user.value?.values?.toList() ?: listOf())
//        rv_feed_chat.adapter = FeedChatAdapter(db.message.value?.values?.toList() ?: listOf())
        binding.rvFeedChat.setFab(MainActivity.fabAction)
    }
}
