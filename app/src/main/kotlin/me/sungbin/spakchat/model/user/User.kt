package me.sungbin.spakchat.model.user

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import me.sungbin.spakchat.R
import me.sungbin.spakchat.module.GlideApp
import java.util.*

/**
 * Created by SungBin on 2020-09-11.
 */

data class User(
    val id: String? = null,
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val profileImage: Uri? = null,
    val profileImageColor: Int? = null,
    val backgroundImage: Uri? = null,
    val statusMessage: String? = null,
    val birthday: Date? = null,
    val lastOnline: Date? = null,
    val isOnline: Boolean? = null,
    val friends: List<String>? = null, // for user-uuid
    val sex: Int? = null,
    val emoji: List<String>? = null, // for emoji-uuid
    val black: List<String>? = null, // for user-uuid
    val accountStatus: Int? = null,
    val isTestMode: Boolean? = null
) {
    private val db = FirebaseStorage.getInstance().reference
    private val storage = FirebaseFirestore.getInstance()

    companion object {
        @JvmStatic
        @BindingAdapter("loadProfile")
        fun loadProfile(imageView: ImageView, user: User) {
            if (!user.isTestMode!!) {
                GlideApp
                    .with(imageView.context)
                    .load(user.profileImage)
                    .into(imageView)
            } else {
                GlideApp
                    .with(imageView.context)
                    .load(ColorDrawable(user.profileImageColor!!))
                    .into(imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("loadStatus")
        fun loadStatus(imageView: ImageView, isOnline: Boolean) {
            imageView.setImageResource(
                if (isOnline) R.drawable.bg_shape_online
                else R.drawable.bg_shape_offline
            )
        }
    }
}