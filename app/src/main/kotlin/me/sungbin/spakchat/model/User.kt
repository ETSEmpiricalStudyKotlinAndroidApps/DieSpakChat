package me.sungbin.spakchat.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
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
    companion object {
        @JvmStatic
        @BindingAdapter("loadProfile")
        fun loadProfile(imageView: ImageView, user: User) {
            // todo: 프사 다운로드 주소 가져오기
        }
    }
}