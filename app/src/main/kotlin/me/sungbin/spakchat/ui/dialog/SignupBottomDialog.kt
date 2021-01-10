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
import com.sungbin.androidutils.extensions.isNotBlank
import com.sungbin.androidutils.util.ToastLength
import com.sungbin.androidutils.util.ToastType
import com.sungbin.androidutils.util.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.LayoutSignupBinding
import me.sungbin.spakchat.model.user.AccountStatus
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.util.ColorUtil
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

@AndroidEntryPoint
class SignupBottomDialog : BottomSheetDialogFragment() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
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
                ToastUtil.show(
                    requireContext(),
                    getString(R.string.signup_input_all),
                    ToastLength.SHORT,
                    ToastType.WARNING
                )
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
                ToastUtil.show(
                    requireContext(),
                    getString(R.string.signup_done),
                    ToastLength.SHORT,
                    ToastType.SUCCESS
                )
                dismiss()
            }
            .addOnFailureListener { exception ->
                ExceptionUtil.except(exception, requireContext())
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