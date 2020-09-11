package me.sungbin.spakchat.ui.dialog

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sungbin.sungbintool.util.ToastUtil
import kotlinx.android.synthetic.main.layout_signup.*
import me.sungbin.spakchat.R


class SignupBottomDialog : BottomSheetDialogFragment() {

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