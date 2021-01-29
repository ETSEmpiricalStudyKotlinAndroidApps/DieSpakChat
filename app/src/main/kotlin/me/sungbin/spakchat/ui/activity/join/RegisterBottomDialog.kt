/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.join

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.androidutils.extensions.afterTextChanged
import me.sungbin.androidutils.extensions.isNotBlank
import me.sungbin.androidutils.extensions.toast
import me.sungbin.spakchat.R
import me.sungbin.spakchat.SpakViewModel
import me.sungbin.spakchat.databinding.LayoutSignupBinding
import me.sungbin.spakchat.di.Firestore
import me.sungbin.spakchat.di.Storage
import me.sungbin.spakchat.model.user.AccountStatus
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.util.ColorUtil
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.extensions.hideWithAnimate
import me.sungbin.spakchat.util.extensions.showWithAnimate
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class RegisterBottomDialog private constructor(private val vm: SpakViewModel) :
    BottomSheetDialogFragment() {

    @Firestore
    @Inject
    lateinit var firestore: FirebaseFirestore

    @Storage
    @Inject
    lateinit var storage: StorageReference

    private lateinit var binding: LayoutSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = LayoutSignupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = false

        binding.ivProfile.setOnClickListener {
            TedPermission.with(requireContext())
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {
                        FishBun
                            .with(requireActivity())
                            .setImageAdapter(GlideAdapter())
                            .setMaxCount(1)
                            .setMinCount(1)
                            .startAlbum()
                    }

                    override fun onPermissionDenied(deniedPermissions: List<String>) {
                        toast(getString(R.string.signup_cant_load_picture))
                    }
                })
                .setRationaleMessage(getString(R.string.signup_need_permission_for_load_picture))
                .setDeniedMessage(R.string.signup_permission_denied)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
        }

        binding.tietPassword.afterTextChanged {
            if (it.toString().isNotBlank()) {
                binding.tilPasswordConfirm.showWithAnimate()
            } else {
                binding.tilPasswordConfirm.hideWithAnimate(true)
            }
        }

        binding.btnSignupDone.setOnClickListener {
            if (binding.tietEmail.isNotBlank() && binding.tietName.isNotBlank() && binding.tietPassword.isNotBlank()) {
                if (binding.ivProfile.tag != null) {
                    storage // 프로필 사진 등록
                        .child("profile/${binding.tietEmail.text}/profile.png")
                        .putFile(Uri.parse(binding.ivProfile.tag.toString()))
                        .addOnSuccessListener {
                            it.storage.downloadUrl.addOnSuccessListener { uri ->
                                upload(uri)
                            }
                        }
                } else {
                    upload()
                }
            } else {
                toast(getString(R.string.signup_input_all))
            }
        }
    }

    private fun upload(profileImageUri: Uri? = null) {
        val email = binding.tietEmail.text.toString()
        val name = binding.tietName.text.toString()
        val password = binding.tietPassword.text.toString()
        val user = User(
            key = "${email.first().toInt()}${
            Random.nextInt(
                10000,
                100000
            )
            }${name.last().toInt()}".toLong(),
            id = "$name${Random.nextInt(10000)}",
            email = email,
            password = EncryptUtil.encrypt(
                EncryptUtil.EncryptType.MD5,
                password
            ),
            name = name,
            profileImage = profileImageUri,
            profileImageColor = ColorUtil.randomColor,
            backgroundImage = null,
            statusMessage = getString(R.string.signin_default_status_message),
            birthday = null,
            lastOnline = null,
            isOnline = true,
            friends = listOf(),
            sex = null,
            emoji = listOf(),
            black = listOf(),
            accountStatus = AccountStatus.UNVARIED,
            isTestMode = false
        )

        firestore.collection("users")
            .document(
                EncryptUtil.encrypt(
                    EncryptUtil.EncryptType.SHA256,
                    email
                ).substring(0..5)
            )
            .set(user)
            .addOnSuccessListener {
                toast(getString(R.string.signup_done))
                dismiss()
            }
            .addOnFailureListener { exception ->
                ExceptionUtil.except(exception, requireContext())
            }
    }

    companion object {
        private lateinit var registerBottomDialog: RegisterBottomDialog
        fun instance(vm: SpakViewModel): RegisterBottomDialog {
            if (!::registerBottomDialog.isInitialized) {
                registerBottomDialog = RegisterBottomDialog(vm)
            }
            return registerBottomDialog
        }
    }
}