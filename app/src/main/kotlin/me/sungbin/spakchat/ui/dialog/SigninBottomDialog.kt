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
import com.sungbin.androidutils.util.ToastLength
import com.sungbin.androidutils.util.ToastType
import com.sungbin.androidutils.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.android.synthetic.main.layout_signin.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.isBlank
import org.jetbrains.anko.support.v4.startActivity
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
@WithFragmentBindings
class SigninBottomDialog : BottomSheetDialogFragment() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.layout_signin, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = false

        btn_signin_done.setOnClickListener {
            if (!tiet_email.isBlank() && !tiet_password.isBlank()) {
                firestore.collection("users")
                    .document(
                        EncryptUtil.encrypt(
                            EncryptUtil.EncryptType.SHA256,
                            tiet_email.text.toString()
                        ).substring(0..5)
                    )
                    .get()
                    .addOnSuccessListener {
                        it?.let {
                            it.toObject(User::class.java)?.run {
                                if (tiet_email.text.toString() == email &&
                                    EncryptUtil.encrypt(
                                        EncryptUtil.EncryptType.MD5,
                                        tiet_password.text.toString()
                                    ) == password
                                ) {
                                    ToastUtil.show(
                                        requireContext(),
                                        "$name 님 환영합니다!",
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