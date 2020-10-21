package me.sungbin.spakchat.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.r0adkll.slidr.Slidr
import com.sungbin.androidutils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_chat.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.adapter.ChatAdapter
import me.sungbin.spakchat.database.DataBaseViewModel
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.message.MessageType
import me.sungbin.spakchat.util.TestUtil
import me.sungbin.spakchat.util.Util
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * Created by SungBin on 2020-09-19.
 */

@AndroidEntryPoint
class ChatActivity : BaseActivity() {

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

    private var rootHeight = 0
    private var keyboardHeight = 0
    private var isEmoticonContainerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()
        Slidr.attach(this)

        // https://wooooooak.github.io/android/2020/07/30/emoticon_container/
        cl_container.viewTreeObserver.addOnGlobalLayoutListener {
            if (rootHeight == 0) rootHeight = cl_container.height
            val visibleFrameSize = Rect()
            cl_container.getWindowVisibleDisplayFrame(visibleFrameSize)
            val heightExceptKeyboard = visibleFrameSize.bottom - visibleFrameSize.top
            if (heightExceptKeyboard < rootHeight) {
                keyboardHeight = rootHeight - heightExceptKeyboard
            }
        }

        val id = intent.getStringExtra("id")

        tv_name += "실험실"

        val messages = ArrayList<Message>()
        val adapter = ChatAdapter(messages)
        rv_chat.adapter = adapter

        iv_back.setOnClickListener {
            finish()
        }

        et_input.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                iv_send.setTint(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
            } else {
                iv_send.setTint(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorTwiceLightGray
                    )
                )
            }
        }

        @Suppress("DEPRECATION")
        et_input.setEndDrawableClickEvent {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            if (!isEmoticonContainerShown) { // 컨테이너 보여주기
                TextViewCompat.setCompoundDrawableTintList(
                    et_input,
                    getColor(R.color.colorGray).toColorStateList()
                )
                tv_test.height = keyboardHeight
                et_input.hideKeyboard()
                doDelay(50L) {
                    tv_test.show()
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
            } else { // 컨테이너 가리기
                et_input.showKeyboard()
                TextViewCompat.setCompoundDrawableTintList(
                    et_input,
                    getColor(R.color.colorLightGray).toColorStateList()
                )
                doDelay(50L) {
                    tv_test.hide(true)
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
            }
            isEmoticonContainerShown = !isEmoticonContainerShown
        }

        iv_send.setOnClickListener {
            if (et_input.text.toString().isNotBlank()) {
                val message = Message(
                    // todo: User() 얻어오는 부분 추가
                    id = Util.generateMessageId(et_input.text.toString(), "my name"),
                    message = et_input.text.toString(),
                    time = Date(),
                    type = MessageType.CHAT,
                    attachment = null,
                    owner = TestUtil.getTestUser,
                    mention = listOf(),
                    messageViewType = Random.nextInt(0, 2)
                )
                messages.add(message)
                adapter.notifyDataSetChanged()
                rv_chat.toBottomScroll()
                et_input.clear()
                // database.child("chat/room/uuid").push().setValue(message)
            }
        }

    }

}