package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.sungbin.androidutils.util.Logger
import com.sungbin.androidutils.util.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sungbin.spakchat.R
import me.sungbin.spakchat.database.UserDatabase
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.model.user.UserEntity
import me.sungbin.spakchat.util.toText
import org.jetbrains.anko.startActivity
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by SungBin on 2020-09-21.
 */

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
    lateinit var storage: StorageReference

    @Inject
    @Named("database")
    lateinit var database: DatabaseReference

    @Inject
    @Named("user-db")
    lateinit var userDb: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        if (NetworkUtil.isNetworkAvailable(applicationContext)) {
            CoroutineScope(Dispatchers.IO).launch {
                firestore.collection("users")
                    .get()
                    .addOnSuccessListener {
                        for (user in it) {
                            user?.let {
                                with(user.toObject(User::class.java)) {
                                    Thread { // todo: 왜 코루틴 안에서 쓰레드를 새로 만들어야 할 까?
                                        Logger.w("why is null...", this)
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
                                            isTestMode = this.isTestMode ?: false
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
                delay(1500)
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