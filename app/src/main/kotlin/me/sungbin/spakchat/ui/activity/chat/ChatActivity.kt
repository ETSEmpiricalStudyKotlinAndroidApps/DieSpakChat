/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.chat

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
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.androidutils.extensions.clear
import me.sungbin.androidutils.extensions.doDelay
import me.sungbin.androidutils.extensions.hide
import me.sungbin.androidutils.extensions.hideKeyboard
import me.sungbin.androidutils.extensions.setEndDrawableClickEvent
import me.sungbin.androidutils.extensions.setTint
import me.sungbin.androidutils.extensions.show
import me.sungbin.androidutils.extensions.showKeyboard
import me.sungbin.androidutils.extensions.toBottomScroll
import me.sungbin.androidutils.extensions.toColorStateList
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.ActivityChatBinding
import me.sungbin.spakchat.di.Firestore
import me.sungbin.spakchat.di.RealtimeDatabase
import me.sungbin.spakchat.di.Storage
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.message.MessageType
import me.sungbin.spakchat.ui.activity.BaseActivity
import me.sungbin.spakchat.util.TestUtil
import me.sungbin.spakchat.util.Util
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class ChatActivity : BaseActivity() {

    @Firestore
    @Inject
    lateinit var firestore: FirebaseFirestore

    @Storage
    @Inject
    lateinit var storage: StorageReference

    @RealtimeDatabase
    @Inject
    lateinit var database: DatabaseReference

    private var rootHeight = 0
    private var keyboardHeight = 0
    private var isEmoticonContainerShown = false

    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatBinding.inflate(layoutInflater)
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

        val adapter = ChatAdapter()
        binding.rvChat.adapter = adapter

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.etInput.doAfterTextChanged {
            if (it.toString().isNotBlank()) {
                binding.ivSend.setTint(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorPrimary
                    )
                )
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
                // messages.add(message)
                binding.rvChat.toBottomScroll()
                binding.etInput.clear()
                // database.child("chat/room/uuid").push().setValue(message)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
