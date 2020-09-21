package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sungbin.spakchat.R
import me.sungbin.spakchat.SpakChat
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.util.ExceptionUtil
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by SungBin on 2020-09-21.
 */

@Suppress("LABEL_NAME_CLASH")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
    lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            firestore.collection("users")
                .get()
                .addOnSuccessListener {
                    for (user in it) {
                        user?.let {
                            user.toObject(User::class.java).run {
                                storage
                                    .child("profile/${email ?: return@let}/profile.png")
                                    .downloadUrl.addOnSuccessListener { profileUri ->
                                        storage.child("background/$email/background.png")
                                            .downloadUrl.addOnSuccessListener { backgroundUri ->
                                                SpakChat.users.postValue(
                                                    hashMapOf(
                                                        email to User(
                                                            id = id,
                                                            email = email,
                                                            password = password,
                                                            name = name,
                                                            profileImage = profileUri,
                                                            backgroundImage = backgroundUri,
                                                            statusMessage = statusMessage,
                                                            birthday = birthday,
                                                            lastOnline = lastOnline,
                                                            isOnline = isOnline,
                                                            friends = friends,
                                                            sex = sex,
                                                            emoji = emoji,
                                                            black = black,
                                                            accountStatus = accountStatus
                                                        )
                                                    )
                                                )
                                            }.addOnFailureListener {
                                                SpakChat.users.postValue(
                                                    hashMapOf(
                                                        email to User(
                                                            id = id,
                                                            email = email,
                                                            password = password,
                                                            name = name,
                                                            profileImage = profileUri,
                                                            backgroundImage = backgroundImage,
                                                            statusMessage = statusMessage,
                                                            birthday = birthday,
                                                            lastOnline = lastOnline,
                                                            isOnline = isOnline,
                                                            friends = friends,
                                                            sex = sex,
                                                            emoji = emoji,
                                                            black = black,
                                                            accountStatus = accountStatus
                                                        )
                                                    )
                                                )
                                            }
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    ExceptionUtil.except(it, applicationContext)
                }
        }
    }

}