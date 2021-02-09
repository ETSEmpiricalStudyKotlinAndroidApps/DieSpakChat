/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.room.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sangcomz.fishbun.FishBun
import me.sungbin.androidutils.extensions.get
import me.sungbin.androidutils.extensions.setFab
import me.sungbin.androidutils.tagableroundimageview.TagableRoundImageView
import me.sungbin.spakchat.GlideApp
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.FragmentChatBinding
import me.sungbin.spakchat.room.Room
import me.sungbin.spakchat.room.RoomAdapter
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.ui.fragment.BaseFragment
import me.sungbin.spakchat.util.KeyManager

class ChatFragment : BaseFragment() {

    private val chatType
        get() = if (binding.tvFriendsChat.currentTextColor == R.color.colorBlack)
            KeyManager.ChatType.FRIENDS else KeyManager.ChatType.OPEN

    private val openChatCreateBottomSheetDialog = OpenChatCreateBottomSheetDialog.instance()

    private val adapter by lazy { RoomAdapter() }

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
            setOnClickListener { createChatRoom() }
        }.show()

        initializeChats(KeyManager.ChatType.FRIENDS)
        initializeChats(KeyManager.ChatType.OPEN)

        binding.rvChat.adapter = adapter
        binding.rvChat.setFab(MainActivity.fabAction)
    }

    private fun createChatRoom() {
        when (chatType) {
            KeyManager.ChatType.OPEN -> {
                openChatCreateBottomSheetDialog.show(parentFragmentManager, "")
            }
            KeyManager.ChatType.FRIENDS -> {
                // todo: 친구 시스템 만들기
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FishBun.FISHBUN_REQUEST_CODE -> {
                val profileImageView =
                    openChatCreateBottomSheetDialog.view?.get(
                        R.id.iv_profile,
                        TagableRoundImageView::class.java
                    )
                val uri =
                    data?.getParcelableArrayListExtra<Uri>(FishBun.INTENT_PATH)?.get(0) ?: return
                profileImageView!!.setPadding(0, 0, 0, 0)
                GlideApp.with(requireContext()).load(uri).into(profileImageView)
                profileImageView.tag = uri
            }
        }
    }

    private fun initializeChats(type: String) {
        database.child("chat/$type")
            .get()
            .addOnSuccessListener { ref ->
                ref.children.forEach {
                    val room = it.getValue(Room::class.java)!!
                    chatVm.openRooms.add(room)
                    adapter.submit(chatVm.openRooms)
                }
            }
    }

    private fun reload() {
        // todo
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
