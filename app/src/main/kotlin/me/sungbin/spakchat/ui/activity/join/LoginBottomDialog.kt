/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.join

/**
 * Created by SungBin on 2020-09-11.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.androidutils.extensions.isNotBlank
import me.sungbin.androidutils.extensions.startActivity
import me.sungbin.androidutils.extensions.toast
import me.sungbin.spakchat.R
import me.sungbin.spakchat.SpakViewModel
import me.sungbin.spakchat.databinding.LayoutSigninBinding
import me.sungbin.spakchat.di.Firestore
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import javax.inject.Inject

@AndroidEntryPoint
class LoginBottomDialog private constructor(private val vm: SpakViewModel) :
    BottomSheetDialogFragment() {

    @Firestore
    @Inject
    lateinit var firestore: FirebaseFirestore

    lateinit var binding: LayoutSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = LayoutSigninBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = false

        binding.btnSigninDone.setOnClickListener {
            if (binding.tietEmail.isNotBlank() && binding.tietPassword.isNotBlank()) {
                val email = binding.tietEmail.text.toString()
                val password = binding.tietPassword.text.toString()
                firestore.collection("users")
                    .document(
                        EncryptUtil.encrypt(
                            EncryptUtil.EncryptType.SHA256,
                            email
                        ).substring(0..5)
                    )
                    .get()
                    .addOnSuccessListener {
                        it?.let {
                            it.toObject(User::class.java)?.run {
                                if (this.email == email &&
                                    EncryptUtil.encrypt(
                                            EncryptUtil.EncryptType.MD5,
                                            password
                                        ) == this.password
                                ) {
                                    vm.me = this
                                    toast(getString(R.string.signin_welcome, name))
                                    startActivity<MainActivity>()
                                } else {
                                    toast(getString(R.string.signin_not_match_email_password))
                                }
                            }
                        } ?: toast(getString(R.string.signin_unknown_email))
                    }
                    .addOnFailureListener {
                        ExceptionUtil.except(it, requireContext())
                    }
            } else {
                toast(getString(R.string.signup_input_all))
            }
        }
    }

    companion object {
        private lateinit var loginBottomDialog: LoginBottomDialog
        fun instance(vm: SpakViewModel): LoginBottomDialog {
            if (!::loginBottomDialog.isInitialized) {
                loginBottomDialog = LoginBottomDialog(vm)
            }
            return loginBottomDialog
        }
    }
}
