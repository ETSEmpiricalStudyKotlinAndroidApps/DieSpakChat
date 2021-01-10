package me.sungbin.spakchat.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.r0adkll.slidr.Slidr
import com.sungbin.androidutils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.R
import me.sungbin.spakchat.adapter.ChatAdapter
import me.sungbin.spakchat.databinding.ActivityChatBinding
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

    private var rootHeight = 0
    private var keyboardHeight = 0
    private var isEmoticonContainerShown = false

    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        Slidr.attach(this)

        // https://wooooooak.github.io/android/2020/07/30/emoticon_container/
        binding.clContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if (rootHeight == 0) rootHeight = binding.clContainer.height
            val visibleFrameSize = Rect()
            binding.clContainer.getWindowVisibleDisplayFrame(visibleFrameSize)
            val heightExceptKeyboard = visibleFrameSize.bottom - visibleFrameSize.top
            if (heightExceptKeyboard < rootHeight) {
                keyboardHeight = rootHeight - heightExceptKeyboard
            }
        }

        val id = intent.getStringExtra("id") // todo: key로 바꾸기

        binding.tvName.text = "실험실"

        val messages = ArrayList<Message>()
        val adapter = ChatAdapter(messages)
        binding.rvChat.adapter = adapter

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.etInput.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                binding.ivSend.setTint(ContextCompat.getColor(applicationContext,
                    R.color.colorPrimary))
            } else {
                binding.ivSend.setTint(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorTwiceLightGray
                    )
                )
            }
        }

        @Suppress("DEPRECATION")
        binding.etInput.setEndDrawableClickEvent {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            if (!isEmoticonContainerShown) { // 컨테이너 보여주기
                TextViewCompat.setCompoundDrawableTintList(
                    binding.etInput,
                    getColor(R.color.colorGray).toColorStateList()
                )
                binding.tvTest.height = keyboardHeight
                binding.etInput.hideKeyboard()
                doDelay(50L) {
                    binding.tvTest.show()
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
            } else { // 컨테이너 가리기
                binding.etInput.showKeyboard()
                TextViewCompat.setCompoundDrawableTintList(
                    binding.etInput,
                    getColor(R.color.colorLightGray).toColorStateList()
                )
                doDelay(50L) {
                    binding.tvTest.hide(true)
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
            }
            isEmoticonContainerShown = !isEmoticonContainerShown
        }

        binding.ivSend.setOnClickListener {
            val inputMessage = binding.etInput.text.toString()
            if (inputMessage.isNotBlank()) {
                val message = Message(
                    // todo: User() 얻어오는 부분 추가
                    key = Util.generateMessageId(inputMessage, "my name"),
                    message = inputMessage,
                    time = Date().time,
                    type = MessageType.CHAT,
                    attachment = null,
                    owner = TestUtil.getTestUser,
                    mention = listOf(),
                    messageViewType = Random.nextInt(0, 2)
                )
                messages.add(message)
                adapter.notifyDataSetChanged()
                binding.rvChat.toBottomScroll()
                binding.etInput.clear()
                // database.child("chat/room/uuid").push().setValue(message)
            }
        }

    }

}