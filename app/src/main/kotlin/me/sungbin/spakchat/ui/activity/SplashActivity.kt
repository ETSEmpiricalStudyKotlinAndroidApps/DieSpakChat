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
import org.jetbrains.anko.startActivity
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

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
    lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (NetworkUtil.isNetworkAvailable(applicationContext)) {
            CoroutineScope(Dispatchers.IO).launch {

                val test = UserEntity(
                    key = null,
                    id = "AA",
                    email = "AAAA",
                    password = "AA",
                    name = Random.nextInt(10000).toString(),
                    profileImage = "AA",
                    profileImageColor = null,
                    backgroundImage = null,
                    statusMessage = null,
                    birthday = null,
                    lastOnline = null,
                    isOnline = null,
                    friends = null,
                    sex = null,
                    emoji = null,
                    black = null,
                    accountStatus = null,
                    isTestMode = null
                )
                db.dao().insert(test)
                Logger.w("room 진행")

                firestore.collection("users")
                    .get()
                    .addOnSuccessListener {
                        for (user in it) {
                            user?.let {
                                with(user.toObject(User::class.java)) {
                                    // todo
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