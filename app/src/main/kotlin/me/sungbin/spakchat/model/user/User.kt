package me.sungbin.spakchat.model.user

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import me.sungbin.spakchat.SpakChat
import me.sungbin.spakchat.modules.GlideApp
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
) {

    private val db = FirebaseStorage.getInstance().reference
    private val storage = FirebaseFirestore.getInstance()

    companion object {
        @JvmStatic
        @BindingAdapter("loadProfile")
        fun loadProfile(imageView: ImageView, user: User) {
            GlideApp
                .with(imageView.context)
                .load(SpakChat.users.value?.get(user.email!!)?.profileImage)
                .into(imageView)
        }
    }
}