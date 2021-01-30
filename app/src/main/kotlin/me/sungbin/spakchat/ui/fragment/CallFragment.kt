/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.TestFragmentBinding
import me.sungbin.spakchat.ui.activity.MainActivity

class CallFragment : BaseFragment() {

    private lateinit var binding: TestFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = TestFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_call)
        }.show()

        binding.tvTest.text = "CallFragment"
    }
}
