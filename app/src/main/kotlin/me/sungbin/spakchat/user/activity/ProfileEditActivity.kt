/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.user.activity

import android.os.Bundle
import com.r0adkll.slidr.Slidr
import me.sungbin.spakchat.databinding.ActivityEditProfileBinding
import me.sungbin.spakchat.ui.activity.BaseActivity

class ProfileEditActivity : BaseActivity() {

    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Slidr.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
