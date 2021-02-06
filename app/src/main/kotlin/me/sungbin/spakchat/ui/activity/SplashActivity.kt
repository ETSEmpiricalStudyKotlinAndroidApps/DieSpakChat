/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sungbin.androidutils.extensions.startActivity
import me.sungbin.androidutils.extensions.toast
import me.sungbin.androidutils.util.NetworkUtil
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.ActivitySplashBinding
import me.sungbin.spakchat.user.activity.join.JoinActivity
import me.sungbin.spakchat.user.database.UserEntity
import me.sungbin.spakchat.user.model.User
import me.sungbin.spakchat.util.ArrayConverter.toText
import me.sungbin.spakchat.util.EncryptUtil
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.KeyManager
import me.sungbin.spakchat.util.PrefUtil

class SplashActivity : BaseActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (NetworkUtil.isNetworkAvailable(applicationContext)) {
            firestore.collection("users")
                .get()
                .addOnSuccessListener {
                    it.forEach { user ->
                        lifecycleScope.launch(Dispatchers.IO) { // todo: Is this the best way to use Coroutines?
                            var entity: UserEntity
                            with(user!!.toObject(User::class.java)) {
                                entity = UserEntity(
                                    key = this.key,
                                    userId = this.userId,
                                    loginId = this.loginId,
                                    password = this.password,
                                    name = this.name,
                                    profileImage = this.profileImage.toString(),
                                    profileImageColor = this.profileImageColor,
                                    backgroundImage = this.backgroundImage.toString(),
                                    statusMessage = this.statusMessage,
                                    birthday = this.birthday,
                                    lastOnline = this.lastOnline,
                                    isOnline = this.isOnline,
                                    rooms = this.rooms.toText(),
                                    friends = this.friends.toText(),
                                    sex = this.sex,
                                    emoji = this.emoji.toText(),
                                    black = this.black.toText(),
                                    accountStatus = this.accountStatus,
                                )
                            }
                            userDb.dao().insert(entity)
                        }
                    }
                    gotoLoginOrMainPage()
                }
                .addOnFailureListener { exception ->
                    ExceptionUtil.except(exception, applicationContext)
                }
        } else {
            // todo: offline 처리 하기 (room 이용)
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.splash_non_internet))
                .setMessage(R.string.splash_need_internet)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.cancel)) { _, _ ->
                    finish()
                }
                .show()
        }
    }

    private fun gotoLoginOrMainPage() { // todo: change naming
        val key = PrefUtil.read(applicationContext, KeyManager.User.KEY, null)
        val id = PrefUtil.read(applicationContext, KeyManager.User.ID, null)
        val password = PrefUtil.read(applicationContext, KeyManager.User.PASSWORD, null)
        if (id == null || password == null || key == null) {
            startActivity<JoinActivity>()
        } else {
            firestore.collection("users")
                .document(key)
                .get()
                .addOnSuccessListener {
                    it!!.toObject(User::class.java)?.run {
                        if (this.loginId == id &&
                            this.password == EncryptUtil.encrypt(
                                    EncryptUtil.EncryptType.MD5,
                                    password
                                )
                        ) {
                            userVm.me = this
                            toast(getString(R.string.login_welcome, name))
                            finish()
                            startActivity<MainActivity>()
                        }
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
