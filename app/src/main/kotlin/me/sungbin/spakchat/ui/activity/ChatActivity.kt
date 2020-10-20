package me.sungbin.spakchat.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
class ChatActivity : AppCompatActivity() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
    lateinit var storage: StorageReference

    @Inject
    @Named("database")
    lateinit var database: DatabaseReference

    private var rootHeight = -1
    private var keyboardHeight = -1
    private var showEmotionContainer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()
        Slidr.attach(this)


        // https://wooooooak.github.io/android/2020/07/30/emoticon_container/
        cl_container.viewTreeObserver.addOnGlobalLayoutListener {
            if (rootHeight == -1) rootHeight = cl_container.height
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
            if (!showEmotionContainer) {
                et_input.hideKeyboard()
                tv_test.height = keyboardHeight
                doDelay(50L) {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    tv_test.show()
                }
            } else {
                et_input.showKeyboard()
                doDelay(50L) {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    tv_test.hide(true)
                }
            }
            showEmotionContainer = !showEmotionContainer
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