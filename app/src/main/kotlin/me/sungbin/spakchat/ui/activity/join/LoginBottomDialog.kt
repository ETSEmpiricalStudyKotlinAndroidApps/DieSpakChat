/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.sungbin.androidutils.extensions.startActivity
import me.sungbin.androidutils.extensions.toast
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutDialogLoginBinding
import me.sungbin.spakchat.user.model.User
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.KeyManager
import me.sungbin.spakchat.util.PrefUtil
import me.sungbin.spakchat.util.Util

class LoginBottomDialog private constructor() : BaseBottomSheetDialogFragment() {

    private var _binding: LayoutDialogLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LayoutDialogLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = false

        binding.btnSigninDone.setOnClickListener {
            val id = binding.tietId.text.toString()
            val password = binding.tietPassword.text.toString()
            if (id.isBlank() || password.isBlank()) {
                toast(getString(R.string.register_input_all))
                return@setOnClickListener
            }
            firestore.collection("users")
                .document(Util.generateUserKey(id, password).toString())
                .get()
                .addOnSuccessListener {
                    it?.let {
                        it.toObject(User::class.java)?.run {
                            if (this.loginId == id &&
                                this.password ==
                                EncryptUtil.encrypt(
                                        EncryptUtil.EncryptType.MD5,
                                        password
                                    )
                            ) {
                                globalVm.me = this
                                toast(getString(R.string.login_welcome, name))
                                PrefUtil.save(requireContext(), KeyManager.User.KEY, key.toString())
                                PrefUtil.save(requireContext(), KeyManager.User.ID, id)
                                PrefUtil.save(requireContext(), KeyManager.User.PASSWORD, password)
                                onDestroy()
                                startActivity<MainActivity>()
                            } else {
                                toast(getString(R.string.login_not_match_id_password))
                            }
                        }
                    } ?: toast(getString(R.string.login_unknown_id))
                }
                .addOnFailureListener {
                    ExceptionUtil.except(it, requireContext())
                }
        }
    }

    companion object {
        private val instance = LoginBottomDialog()
        fun instance() = instance
    }

    override fun onDestroy() {
        super.onDestroy()
        dismiss()
        _binding = null
    }
}
