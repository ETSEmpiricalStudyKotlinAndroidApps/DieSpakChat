package me.sungbin.spakchat.model.user

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import me.sungbin.spakchat.util.ColorUtil
import java.util.*


/**
 * Created by SungBin on 2020-09-11.
 */


data class User(
    val id: String? = null,
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val profileImage: String? = null,
    val backgroundImage: String? = null,
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
            // todo: 프사 다운로드 주소 가져오기
            imageView.setBackgroundColor(ColorUtil.randomColor)

            /*GlideApp
                .with(imageView.context)
                .load(ColorUtil.randomColor)
                .into(imageView)*/
        }
    }
}