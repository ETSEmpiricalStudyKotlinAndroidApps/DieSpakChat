package me.sungbin.spakchat.model.message

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sungbin.sungbintool.extensions.plusAssign
import me.sungbin.spakchat.R
import me.sungbin.spakchat.SpakChat
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.module.GlideApp
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by SungBin on 2020-09-18.
 */

data class Message(
    val id: String? = null,
    val message: String? = null,
    val time: Date? = null,
    val type: Int? = null,
    val attachment: Int? = null,
    val owner: User? = null,
    val mention: List<User>? = null,
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

        @JvmStatic
        @BindingAdapter("loadMessage")
        fun loadMessage(textView: TextView, message: Message) {
            // todo: 메시지 타입 가져와서 처리하기
            textView += message.message ?: "unknown message"
        }

        @JvmStatic
        @BindingAdapter("loadDate")
        fun loadData(textView: TextView, date: Date) {
            val formatter = SimpleDateFormat("aa hh:mm", Locale.KOREA)
            val time = formatter.format(date)
            textView += time
        }

        @JvmStatic
        @BindingAdapter("loadLastOnline")
        fun loadLastOnline(textView: TextView, date: Date) {
            textView.text = when {
                date.time - Date().time in 0..(1000 * 60 * 30) -> "방금 전 까지 접속"
                else -> {
                    val formatter = SimpleDateFormat("MM.dd kk:mm 까지 접속", Locale.KOREA)
                    formatter.format(date)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("loadUnreadCount")
        fun loadUnreadCount(textView: TextView, message: Message) {
            // todo: 메시지 안읽은 수 구하기

        }

        @JvmStatic
        @BindingAdapter("loadStatus")
        fun loadStatus(imageView: ImageView, message: Message) {
            imageView.setImageResource(
                if (SpakChat.users.value?.get(message.owner?.email)?.isOnline == true) R.drawable.bg_shape_online
                else R.drawable.bg_shape_offline
            )
        }
    }
}