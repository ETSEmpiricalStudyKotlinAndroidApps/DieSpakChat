/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.join

import android.Manifest
import android.app.AlertDialog
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
import me.sungbin.androidutils.extensions.isNotBlank
import me.sungbin.androidutils.extensions.toast
import me.sungbin.androidutils.util.Logger
import me.sungbin.spakchat.R
import me.sungbin.spakchat.SpakViewModel
import me.sungbin.spakchat.databinding.LayoutDialogRegisterBinding
import me.sungbin.spakchat.databinding.LayoutDialogRegisterConfirmCodeBinding
import me.sungbin.spakchat.di.Firestore
import me.sungbin.spakchat.di.Storage
import me.sungbin.spakchat.model.user.AccountStatus
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.util.ColorUtil
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.Gmail
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

    private lateinit var binding: LayoutDialogRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = LayoutDialogRegisterBinding.inflate(inflater)
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
                        toast(getString(R.string.register_cant_load_picture))
                    }
                })
                .setRationaleMessage(getString(R.string.register_need_permission_for_load_picture))
                .setDeniedMessage(R.string.register_permission_denied)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
        }

        binding.btnSignupDone.setOnClickListener {
            if (binding.tietEmail.isNotBlank() && binding.tietName.isNotBlank() && binding.tietPassword.isNotBlank()) {
                val code = Random.nextInt(10000, 100000).toString()
                Gmail.send(
                    binding.tietEmail.text.toString(),
                    "SpakChat 회원가입 인증코드",
                    "SpakChat 인증코드 입니다.\n\n\n[ $code ]\n\n\n위 코드를 사용해 주세요."
                )
                val layout = LayoutDialogRegisterConfirmCodeBinding.inflate(layoutInflater)
                val dialog = AlertDialog.Builder(requireActivity())
                dialog.setTitle("인증코드")
                dialog.setView(layout.root)
                dialog.setCancelable(false)
                dialog.setPositiveButton("인증") { _, _ ->
                    if (code == layout.etConfirmCode.text.toString()) {
                        if (binding.ivProfile.tag != null) {
                            storage // 프로필 사진 등록
                                .child("profile/${binding.tietEmail.text}/profile.png")
                                .putFile(Uri.parse(binding.ivProfile.tag.toString()))
                                .addOnSuccessListener {
                                    it.storage.downloadUrl.addOnSuccessListener { uri ->
                                        upload(uri)
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    ExceptionUtil.except(exception, requireContext())
                                }
                        } else {
                            upload()
                        }
                    } else {
                        toast("인증코드가 잘못되었어요.")
                    }
                }
                dialog.show()
            } else {
                toast(getString(R.string.register_input_all))
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
            profileImage = profileImageUri.toString(),
            profileImageColor = ColorUtil.randomColor,
            backgroundImage = null,
            statusMessage = getString(R.string.login_default_status_message),
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
                toast(getString(R.string.register_done))
                dismiss()
            }
            .addOnFailureListener { exception ->
                Logger.e(exception.localizedMessage)
                // ExceptionUtil.except(exception, requireContext())
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
