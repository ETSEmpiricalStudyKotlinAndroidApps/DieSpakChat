/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.ui.activity.chat

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.r0adkll.slidr.Slidr
import java.util.Date
import me.sungbin.androidutils.extensions.clear
import me.sungbin.androidutils.extensions.doDelay
import me.sungbin.androidutils.extensions.hide
import me.sungbin.androidutils.extensions.hideKeyboard
import me.sungbin.androidutils.extensions.setEndDrawableClickEvent
import me.sungbin.androidutils.extensions.setTint
import me.sungbin.androidutils.extensions.show
import me.sungbin.androidutils.extensions.showKeyboard
import me.sungbin.androidutils.extensions.toColorStateList
import me.sungbin.androidutils.util.Logger
import me.sungbin.spakchat.GlideApp
import me.sungbin.spakchat.R
import me.sungbin.spakchat.databinding.ActivityChatBinding
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.message.MessageType
import me.sungbin.spakchat.model.message.MessageViewType
import me.sungbin.spakchat.model.user.User
import me.sungbin.spakchat.ui.activity.BaseActivity
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.KeyManager
import me.sungbin.spakchat.util.Util

class ChatActivity : BaseActivity() {

    private var isNewMessage = false
    private val adapter by lazy { ChatAdapter(globalVm.me) }
    private lateinit var databaseReference: DatabaseReference

    private var rootHeight = 0
    private var keyboardHeight = 0
    private var isEmoticonContainerShown = false

    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var friend: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_chat,
            ConstraintLayout(this),
            false
        )
        setContentView(binding.root)

        val friendKey = intent.getLongExtra(KeyManager.User.KEY, -1)
        databaseReference = database.child("chat/${globalVm.me.key}/friend/$friendKey")
        supportActionBar?.hide()
        Slidr.attach(this)
        friend = globalVm.users.find {
            it.key == friendKey
        }!!

        initBinding()

        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)!!
                chatVm.messagesMap[friend.key!!]?.let { messagesCache ->
                    if (isNewMessage) {
                        updateMessage(message)
                    } else {
                        if (message.key == messagesCache.last().key) {
                            isNewMessage = true // 다음 메시지부터 받기
                        }
                    }
                } ?: run {
                    isNewMessage = true
                    updateMessage(message)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // todo: message edit
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // todo: message removed
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                ExceptionUtil.except(error.toException(), this@ChatActivity)
            }
        })
    }

    private fun initBinding() {
        adapter.submit(chatVm.messagesMap[friend.key] ?: listOf())

        binding.user = friend
        binding.rvChat.adapter = adapter
        binding.ivBack.setOnClickListener { finish() }

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
                    key = Util.generateMessageKey(inputMessage, globalVm.me.name!!),
                    message = inputMessage,
                    time = Date().time,
                    type = MessageType.CHAT,
                    attachment = null,
                    owner = globalVm.me,
                    mention = listOf(),
                    messageViewType = MessageViewType.NORMAL
                )
                binding.etInput.clear()
                databaseReference.push().setValue(message)
            }
        }

        // 임시
        GlideApp.with(applicationContext).load(ColorDrawable(-150744)).into(binding.ivRoomCover)
    }

    private fun updateMessage(message: Message) {
        chatVm.messagesMap[friend.key!!]?.add(message) ?: run {
            chatVm.messagesMap[friend.key!!] = mutableListOf(message)
        }
        adapter.submit(chatVm.messagesMap[friend.key!!]!!)
        binding.rvChat.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Logger.d("onDestroy", "ChatActivity")
    }
}
