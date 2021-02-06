/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.sungbin.androidutils.extensions.setFab
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.FragmentChatBinding
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.ui.fragment.BaseFragment

class ChatFragment : BaseFragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_chat)
        }.show()

        binding.rvChat.setFab(MainActivity.fabAction)
    }

    private fun reload() {
        // todo
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
