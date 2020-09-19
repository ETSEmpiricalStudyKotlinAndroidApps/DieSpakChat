package me.sungbin.spakchat.model

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sungbin.sungbintool.extensions.plusAssign
import me.sungbin.spakchat.R
import me.sungbin.spakchat.util.ColorUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt


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
    companion object {
        @JvmStatic
        @BindingAdapter("loadProfile")
        fun loadProfile(imageView: ImageView, url: String) {
            // todo: 프사 다운로드 주소 가져오기
            imageView.setBackgroundColor(ColorUtil.randomColor)

            /*GlideApp
                .with(imageView.context)
                .load(R.mipmap.ic_launcher)
                .into(imageView)*/
        }

        @JvmStatic
        @BindingAdapter("loadMessage")
        fun loadMessage(textView: TextView, message: Message) {
            // todo: 메시지 타입 가져와서 처리하기
            textView += message.message.toString()
        }

        @JvmStatic
        @BindingAdapter("loadDate")
        fun loadData(textView: TextView, date: Date) {
            // todo: date로 부터 시간 가져오기
            val formatter = SimpleDateFormat("aa hh:mm", Locale.KOREA)
            val time = formatter.format(date)
            textView += time
        }

        @JvmStatic
        @BindingAdapter("loadLastOnline")
        fun loadLastOnline(textView: TextView, date: Date) {
            // todo: date로 부터 시간 가져오기
        }

        @JvmStatic
        @BindingAdapter("loadUnreadCount")
        fun loadUnreadCount(textView: TextView, message: Message) {
            // todo: 메시지 안읽은 수 구하기
            textView += Random.nextInt(0..100).toString()
        }

        @JvmStatic
        @BindingAdapter("loadStatus")
        fun loadStatus(imageView: ImageView, message: Message) {
            // todo: 온라인/오프라인 표시
            imageView.setImageResource(
                if (message.owner!!.isOnline!!) R.drawable.bg_shape_online
                else R.drawable.bg_shape_offline
            )
        }
    }
}