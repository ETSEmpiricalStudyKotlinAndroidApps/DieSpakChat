package me.sungbin.spakchat.model.message

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import me.sungbin.spakchat.R
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.module.GlideApp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * Created by SungBin on 2020-09-18.
 */

data class Message(
    val key: Long? = null,
    val message: String? = null,
    val time: Long? = null, // Date().time
    val type: Int? = null,
    val attachment: Int? = null,
    val owner: User? = null,
    val mention: List<Long>? = null, // for user-key
    val messageViewType: Int? = null
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
                    .load(user.profileImage ?: ColorDrawable(user.profileImageColor!!))
                    .into(imageView)
            } else {
                GlideApp
                    .with(imageView.context)
                    .load(ColorDrawable(user.profileImageColor!!))
                    .into(imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("loadMessage")
        fun loadMessage(textView: TextView, message: Message) {
            // todo: 메시지 타입 가져와서 처리하기
            textView.text = message.message ?: "unknown message"
        }

        @JvmStatic
        @BindingAdapter("loadDate")
        fun loadData(textView: TextView, time: Long) {
            val formatter = SimpleDateFormat("aa hh:mm", Locale.KOREA)
            val date = Date()
            date.time = time
            textView.text = formatter.format(date)
        }

        @JvmStatic
        @BindingAdapter("loadLastOnline")
        fun loadLastOnline(textView: TextView, time: Long) {
            textView.text = when {
                time - Date().time in 0..(1000 * 60 * 30) -> "방금 전 까지 접속"
                else -> {
                    val formatter = SimpleDateFormat("MM.dd kk:mm 까지 접속", Locale.KOREA)
                    val date = Date()
                    date.time = time
                    formatter.format(date)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("loadUnreadCount")
        fun loadUnreadCount(textView: TextView, message: Message) {
            if (message.owner?.isTestMode!!) {
                textView.text = Random.nextInt(0, 100).toString()
            }
        }

        @JvmStatic
        @BindingAdapter("loadStatus")
        fun loadStatus(imageView: ImageView, message: Message) {
            imageView.setImageResource(
                if (message.owner?.isOnline == true) R.drawable.bg_shape_online
                else R.drawable.bg_shape_offline
            )
        }
    }
}
