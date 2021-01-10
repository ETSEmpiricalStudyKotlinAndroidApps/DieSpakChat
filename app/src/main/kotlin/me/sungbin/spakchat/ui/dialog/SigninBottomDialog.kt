package me.sungbin.spakchat.ui.dialog


/**
 * Created by SungBin on 2020-09-11.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.sungbin.androidutils.extensions.isNotBlank
import com.sungbin.androidutils.util.ToastLength
import com.sungbin.androidutils.util.ToastType
import com.sungbin.androidutils.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.R
import me.sungbin.spakchat.SpakChat
import me.sungbin.spakchat.databinding.LayoutSigninBinding
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import org.jetbrains.anko.support.v4.startActivity
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class SigninBottomDialog : BottomSheetDialogFragment() {

    @Inject
    @Named("firestore")
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
                                    SpakChat.me = this
                                    ToastUtil.show(
                                        requireContext(),
                                        getString(R.string.signin_welcome, name),
                                        ToastLength.SHORT,
                                        ToastType.SUCCESS
                                    )
                                    startActivity<MainActivity>()
                                } else {
                                    ToastUtil.show(
                                        requireContext(),
                                        getString(R.string.signin_not_match_email_password),
                                        ToastLength.SHORT,
                                        ToastType.WARNING
                                    )
                                }
                            }
                        } ?: ToastUtil.show(
                            requireContext(),
                            getString(R.string.signin_unknown_email),
                            ToastLength.SHORT,
                            ToastType.WARNING
                        )
                    }
                    .addOnFailureListener {
                        ExceptionUtil.except(it, requireContext())
                    }
            } else {
                ToastUtil.show(
                    requireContext(),
                    getString(R.string.signup_input_all),
                    ToastLength.SHORT,
                    ToastType.WARNING
                )
            }
        }
    }

    companion object {
        private lateinit var bottomSheetDialogFragment: BottomSheetDialogFragment

        fun instance(): BottomSheetDialogFragment {
            if (!::bottomSheetDialogFragment.isInitialized) {
                bottomSheetDialogFragment = SigninBottomDialog()
            }
            return bottomSheetDialogFragment
        }
    }
}