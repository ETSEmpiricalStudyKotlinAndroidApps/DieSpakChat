package me.sungbin.spakchat.ui.dialog

/**
 * Created by SungBin on 2020-09-11.
 */

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.androidutils.extensions.isNotBlank
import me.sungbin.androidutils.extensions.toast
import me.sungbin.spakchat.R
import me.sungbin.spakchat.SpakViewModel
import me.sungbin.spakchat.databinding.LayoutSigninBinding
import me.sungbin.spakchat.di.Firestore
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class SigninBottomDialog private constructor(private val vm: SpakViewModel) :
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
        private lateinit var signinBottomDialog: SigninBottomDialog
        fun instance(vm: SpakViewModel): SigninBottomDialog {
            if (!::signinBottomDialog.isInitialized) {
                signinBottomDialog = SigninBottomDialog(vm)
            }
            return signinBottomDialog
        }
    }

    @Throws(Exception::class)
    private inline fun <reified T> Fragment.startActivity(
        isNewTask: Boolean = false,
        vararg extras: Pair<String, *>
    ) {
        requireActivity().startActivity(
            Intent(requireActivity(), T::class.java).apply {
                if (isNewTask) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                extras.iterator().forEach {
                    when (it.second) {
                        is Int -> putExtra(it.first, it.second as Int)
                        is Long -> putExtra(it.first, it.second as Long)
                        is Char -> putExtra(it.first, it.second as Char)
                        is Byte -> putExtra(it.first, it.second as Byte)
                        is Short -> putExtra(it.first, it.second as Short)
                        is Float -> putExtra(it.first, it.second as Float)
                        is Double -> putExtra(it.first, it.second as Double)
                        is Boolean -> putExtra(it.first, it.second as Boolean)
                        is String? -> putExtra(it.first, it.second as String?)
                        is Bundle? -> putExtra(it.first, it.second as Bundle?)
                        is IntArray? -> putExtra(it.first, it.second as IntArray?)
                        is CharArray? -> putExtra(it.first, it.second as CharArray?)
                        is LongArray? -> putExtra(it.first, it.second as LongArray?)
                        is ByteArray? -> putExtra(it.first, it.second as ByteArray?)
                        is ShortArray? -> putExtra(it.first, it.second as ShortArray?)
                        is Parcelable? -> putExtra(it.first, it.second as Parcelable?)
                        is FloatArray? -> putExtra(it.first, it.second as FloatArray?)
                        is DoubleArray? -> putExtra(it.first, it.second as DoubleArray?)
                        is CharSequence? -> putExtra(it.first, it.second as CharSequence?)
                        is Serializable? -> putExtra(it.first, it.second as Serializable?)
                        is BooleanArray? -> putExtra(it.first, it.second as BooleanArray?)
                        else -> throw Exception("${it.first} pair second value(${it.second}) is not supported type.")
                    }
                }
            }
        )
    }
}
