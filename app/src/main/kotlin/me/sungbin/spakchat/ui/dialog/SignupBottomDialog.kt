package me.sungbin.spakchat.ui.dialog

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sungbin.sungbintool.util.ToastUtil
import kotlinx.android.synthetic.main.layout_signup.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.model.AccountStatus
import me.sungbin.spakchat.model.User
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.isBlank


class SignupBottomDialog : BottomSheetDialogFragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.layout_signup, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                            ToastUtil.SHORT,
                            ToastUtil.WARNING
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
                val user = User(
                    uuid = null,
                    id = null,
                    email = tiet_email.text.toString(),
                    password = tiet_password.text.toString(),
                    name = tiet_name.text.toString(),
                    profileImage = null,
                    backgroundImage = null,
                    statusMessage = "새로 가입했어요!",
                    birthday = null,
                    lastOnline = null,
                    isOnline = true,
                    friends = listOf(),
                    sex = null,
                    emoji = listOf(),
                    black = listOf(),
                    accountStatus = AccountStatus.NORMAL,
                )

                db.collection("users")
                    .document(tiet_email.text.toString())
                    .set(user)
                    .addOnSuccessListener {
                        ToastUtil.show(
                            requireContext(),
                            getString(R.string.signup_done),
                            ToastUtil.SHORT,
                            ToastUtil.SUCCESS
                        )
                        dismiss()
                    }
                    .addOnFailureListener {
                        ExceptionUtil.except(it, requireContext())
                    }
            } else {
                ToastUtil.show(
                    requireContext(),
                    getString(R.string.signup_input_all),
                    ToastUtil.SHORT,
                    ToastUtil.WARNING
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