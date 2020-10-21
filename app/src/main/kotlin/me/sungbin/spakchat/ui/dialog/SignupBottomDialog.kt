package me.sungbin.spakchat.ui.dialog

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
import com.sungbin.androidutils.util.ToastLength
import com.sungbin.androidutils.util.ToastType
import com.sungbin.androidutils.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.android.synthetic.main.layout_signup.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.model.user.AccountStatus
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.util.ColorUtil
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.isBlank
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
@WithFragmentBindings
class SignupBottomDialog : BottomSheetDialogFragment() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
    lateinit var storage: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.layout_signup, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = false

        iv_profile.setOnClickListener {
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
                        ToastUtil.show(
                            requireContext(),
                            getString(R.string.signup_cant_load_picture),
                            ToastLength.SHORT,
                            ToastType.WARNING
                        )
                    }
                })
                .setRationaleMessage(getString(R.string.signup_need_permission_for_load_picture))
                .setDeniedMessage(R.string.signup_permission_denied)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
        }

        btn_signup_done.setOnClickListener {
            if (!tiet_email.isBlank() && !tiet_name.isBlank() && !tiet_password.isBlank()) {
                if (iv_profile.tag != null) {
                    storage // 프로필 사진 등록
                        .child("profile/${tiet_email.text}/profile.png")
                        .putFile(Uri.parse(iv_profile.tag.toString()))
                        .addOnSuccessListener {
                            it.storage.downloadUrl.addOnSuccessListener { uri ->
                                val user = User(
                                    id = null,
                                    email = tiet_email.text.toString(),
                                    password = EncryptUtil.encrypt(
                                        EncryptUtil.EncryptType.MD5,
                                        tiet_password.text.toString()
                                    ),
                                    name = tiet_name.text.toString(),
                                    profileImage = uri,
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
                                )

                                firestore.collection("users")
                                    .document(
                                        EncryptUtil.encrypt(
                                            EncryptUtil.EncryptType.SHA256,
                                            tiet_email.text.toString()
                                        ).substring(0..5)
                                    )
                                    .set(user)
                                    .addOnSuccessListener {
                                        ToastUtil.show(
                                            requireContext(),
                                            getString(R.string.signup_done),
                                            ToastLength.SHORT,
                                            ToastType.SUCCESS
                                        )
                                        dismiss()
                                    }
                                    .addOnFailureListener {
                                        ExceptionUtil.except(it, requireContext())
                                    }
                            }
                        }
                } else {
                    val user = User(
                        id = null,
                        email = tiet_email.text.toString(),
                        password = EncryptUtil.encrypt(
                            EncryptUtil.EncryptType.MD5,
                            tiet_password.text.toString()
                        ),
                        name = tiet_name.text.toString(),
                        profileImage = null,
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
                        accountStatus = AccountStatus.UNVARIED
                    )

                    firestore.collection("users")
                        .document(
                            EncryptUtil.encrypt(
                                EncryptUtil.EncryptType.SHA256,
                                tiet_email.text.toString()
                            ).substring(0..5)
                        )
                        .set(user)
                        .addOnSuccessListener {
                            ToastUtil.show(
                                requireContext(),
                                getString(R.string.signup_done),
                                ToastLength.SHORT,
                                ToastType.SUCCESS
                            )
                            dismiss()
                        }
                        .addOnFailureListener {
                            ExceptionUtil.except(it, requireContext())
                        }
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
                bottomSheetDialogFragment = SignupBottomDialog()
            }
            return bottomSheetDialogFragment
        }
    }
}