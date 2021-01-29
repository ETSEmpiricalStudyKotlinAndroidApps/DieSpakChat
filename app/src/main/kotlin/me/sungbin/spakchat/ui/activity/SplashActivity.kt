/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.androidutils.extensions.doDelay
import me.sungbin.androidutils.extensions.startActivity
import me.sungbin.androidutils.util.Logger
import me.sungbin.androidutils.util.NetworkUtil
import me.sungbin.spakchat.R
import me.sungbin.spakchat.database.UserDatabase
import me.sungbin.spakchat.databinding.ActivitySplashBinding
import me.sungbin.spakchat.di.Firestore
import me.sungbin.spakchat.di.UserDB
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.model.user.UserEntity
import me.sungbin.spakchat.ui.activity.join.JoinActivity
import me.sungbin.spakchat.util.toText
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Firestore
    @Inject
    lateinit var firestore: FirebaseFirestore

    @UserDB
    @Inject
    lateinit var userDb: UserDatabase

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (NetworkUtil.isNetworkAvailable(applicationContext)) {
            firestore.collection("users")
                .get()
                .addOnSuccessListener {
                    for (user in it) {
                        user?.let {
                            with(user.toObject(User::class.java)) {
                                Thread {
                                    val entity = UserEntity(
                                        key = this.key,
                                        id = this.id,
                                        email = this.email,
                                        password = this.password,
                                        name = this.name,
                                        profileImage = this.profileImage.toString(),
                                        profileImageColor = this.profileImageColor,
                                        backgroundImage = this.backgroundImage.toString(),
                                        statusMessage = this.statusMessage,
                                        birthday = this.birthday,
                                        lastOnline = this.lastOnline,
                                        isOnline = this.isOnline,
                                        friends = this.friends.toText(),
                                        sex = this.sex,
                                        emoji = this.emoji.toText(),
                                        black = this.black.toText(),
                                        accountStatus = this.accountStatus,
                                        isTestMode = this.isTestMode
                                    )
                                    userDb.dao().insert(entity)
                                }.start()
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    // ExceptionUtil.except(it, applicationContext)
                    Logger.e(it)
                }
            doDelay(1000) {
                startActivity<JoinActivity>()
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
}
