package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.sungbin.androidutils.util.Logger
import com.sungbin.androidutils.util.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sungbin.spakchat.R
import me.sungbin.spakchat.database.DataBaseViewModel
import me.sungbin.spakchat.model.user.User
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

    val db by viewModels<DataBaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        db.init(this)

        if (NetworkUtil.isNetworkAvailable(applicationContext)) {
            CoroutineScope(Dispatchers.IO).launch {
                firestore.collection("users")
                    .get()
                    .addOnSuccessListener {
                        for (user in it) {
                            user?.let {
                                with(user.toObject(User::class.java)) {
                                    Logger.w("AAA")
                                    db.user.postValue(
                                        hashMapOf(
                                            (this.id ?: return@let) to this
                                        )
                                    )
                                    Logger.w(
                                        hashMapOf(
                                            this.id to this
                                        )
                                    )
                                }
                            }
                        }
                    }
                    .addOnFailureListener {
                        // ExceptionUtil.except(it, applicationContext)
                        Logger.e(it)
                    }
            }
            startActivity<JoinActivity>()
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