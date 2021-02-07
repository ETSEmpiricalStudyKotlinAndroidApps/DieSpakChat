/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.user

import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sungbin.androidutils.util.Logger
import me.sungbin.spakchat.annotation.ApplicationContext
import me.sungbin.spakchat.user.database.UserDatabase
import me.sungbin.spakchat.user.database.UserEntity
import me.sungbin.spakchat.user.model.User
import me.sungbin.spakchat.util.ArrayConverter.toText
import me.sungbin.spakchat.util.ExceptionUtil

class UserUtil private constructor(
    private val firestore: FirebaseFirestore,
    private val userDb: UserDatabase,
    @ApplicationContext private val context: Context,
) {

    fun joinRoom(user: User, roomKey: Long) {
        if (!user.rooms!!.contains(roomKey)) {
            user.rooms.add(roomKey)
            firestore.collection("users")
                .document(user.key.toString())
                .set(user)
                .addOnSuccessListener {
                    updateDatabase(user)
                    Logger.i("UserUtil.joinRoom(${user.name})", "방 입장 완료: $roomKey")
                }
                .addOnFailureListener { exception ->
                    ExceptionUtil.except(exception, context)
                }
        }
    }

    private fun updateDatabase(user: User) {
        CoroutineScope(Dispatchers.IO).launch { // todo: Is this the best way to use Coroutines?
            var entity: UserEntity
            with(user) {
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

    companion object {
        @SuppressLint("StaticFieldLeak") // 싱글톤에 application-context라 괜찮지 않나?
        private lateinit var userUtil: UserUtil
        fun instance(
            firestore: FirebaseFirestore,
            userDb: UserDatabase,
            @ApplicationContext context: Context,
        ): UserUtil {
            if (!::userUtil.isInitialized) {
                userUtil = UserUtil(firestore, userDb, context)
            }
            return userUtil
        }
    }
}
