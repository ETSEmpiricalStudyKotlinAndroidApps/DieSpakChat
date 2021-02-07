/*
 * Create by Sungbin Ji on 2021. 2. 7.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.fragment

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import me.sungbin.androidutils.extensions.clear
import me.sungbin.androidutils.extensions.toast
import me.sungbin.spakchat.GlideApp
import me.sungbin.spakchat.R
import me.sungbin.spakchat.chat.room.Room
import me.sungbin.spakchat.databinding.LayoutDialogCreateChatBinding
import me.sungbin.spakchat.ui.fragment.BaseBottomSheetDialogFragment
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.KeyManager
import me.sungbin.spakchat.util.Util

class OpenChatCreateBottomSheetDialog private constructor() : BaseBottomSheetDialogFragment() {

    private var _binding: LayoutDialogCreateChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LayoutDialogCreateChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivRoomCover.setOnClickListener {
            TedPermission.with(requireContext())
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {
                        FishBun
                            .with(requireParentFragment())
                            .setImageAdapter(GlideAdapter())
                            .setMaxCount(1)
                            .setMinCount(1)
                            .startAlbum()
                    }

                    override fun onPermissionDenied(deniedPermissions: List<String>) {
                        toast(getString(R.string.register_cant_load_picture))
                    }
                })
                .setRationaleMessage(getString(R.string.register_need_permission_for_load_picture))
                .setDeniedMessage(R.string.register_permission_denied)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
        }

        binding.tvTitle.text = getString(
            R.string.fragment_chat_dialog_create_chat,
            getString(
                R.string.fragment_chat_openchat
            )
        )

        binding.btnCreateDone.setOnClickListener {
            binding.tietName.error = null

            val name = binding.tietName.text.toString()
            val joinCode = binding.tietJoinCode.text.toString()

            if (name.isBlank()) {
                binding.tietName.error = getString(R.string.chat_fragment_dialog_empty_name)
            } else {
                val key = Util.generateRandomKey(userVm.me.name!!, name)
                if (binding.ivRoomCover.tag != null) {
                    val imageUri = binding.ivRoomCover.tag.toString()
                    storage // 프로필 사진 등록
                        .child("room/$key/cover.${imageUri.substringAfterLast(".")}")
                        .putFile(imageUri.toUri())
                        .addOnSuccessListener {
                            it.storage.downloadUrl.addOnSuccessListener { uri ->
                                createRoom(key, name, joinCode, uri)
                            }
                        }
                        .addOnFailureListener { exception ->
                            ExceptionUtil.except(exception, requireContext())
                        }
                } else {
                    createRoom(key, name, joinCode)
                }
            }
        }
    }

    private fun createRoom(key: Long, name: String, joinCode: String, coverUri: Uri? = null) {
        val room = Room(
            key = key,
            name = name,
            joinCode = joinCode,
            roomCoverImage = coverUri.toString(),
            kick = null
        )
        database.child("chat/${KeyManager.ChatType.OPEN}").push().setValue(room)
        binding.tietName.clear()
        binding.tietJoinCode.clear()
        binding.ivRoomCover.setPadding(16, 16, 16, 16)
        GlideApp.with(requireContext()).load(R.drawable.ic_outline_camera_alt_24)
            .into(binding.ivRoomCover)
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val instance = OpenChatCreateBottomSheetDialog()
        fun instance() = instance
    }
}
