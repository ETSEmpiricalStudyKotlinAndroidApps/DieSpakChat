package me.sungbin.spakchat.model

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*


/**
 * Created by SungBin on 2020-09-18.
 */

data class Message(
    val id: String? = null,
    val message: String? = null,
    val time: Date? = null,
    val type: MessageType? = null,
    val attachment: Attachment? = null,
    val owner: User? = null,
    val mention: List<User>? = null,
) {
    companion object {
        @JvmStatic
        @BindingAdapter("loadProfile")
        fun loadProfile(imageView: ImageView, url: String) {
            // todo: 프사 다운로드 주소 가져오기
        }

        @JvmStatic
        @BindingAdapter("loadMessage")
        fun loadMessage(textView: TextView, message: Message) {
            // todo: 메시지 타입 가져와서 처리하기
        }

        @JvmStatic
        @BindingAdapter("loadDate")
        fun loadData(textView: TextView, date: Date) {
            // todo: date로 부터 시간 가져오기
        }

        @JvmStatic
        @BindingAdapter("loadUnreadCount")
        fun loadUnreadCount(textView: TextView, message: Message) {
            // todo: 메시지 안읽은 수 구하기
        }

        @JvmStatic
        @BindingAdapter("loadStatus")
        fun loadStatus(imageView: ImageView, message: Message) {
            // todo: 온라인/오프라인 표시
        }
    }
}