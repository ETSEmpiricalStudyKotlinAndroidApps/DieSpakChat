package me.sungbin.spakchat.ui.dialog


/**
 * Created by SungBin on 2020-09-11.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.sungbin.spakchat.R


class SigninBottomDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.layout_signin, container, false)!!

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