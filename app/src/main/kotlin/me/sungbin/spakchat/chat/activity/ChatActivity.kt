/*
 * Create by Sungbin Ji on 2021. 2. 6.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.chat.activity

import android.graphics.Rect
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
import me.sungbin.androidutils.extensions.toBottomScroll
import me.sungbin.androidutils.extensions.toColorStateList
import me.sungbin.spakchat.R
import me.sungbin.spakchat.chat.model.Chat
import me.sungbin.spakchat.chat.model.ChatType
import me.sungbin.spakchat.chat.model.ChatViewType
import me.sungbin.spakchat.chat.room.Room
import me.sungbin.spakchat.databinding.ActivityChatBinding
import me.sungbin.spakchat.ui.activity.BaseActivity
import me.sungbin.spakchat.util.ExceptionUtil
import me.sungbin.spakchat.util.KeyManager
import me.sungbin.spakchat.util.Util

class ChatActivity : BaseActivity() {

    private var isNewMessage = false
    private val adapter by lazy { ChatAdapter(userVm.me) }
    private lateinit var databaseReference: DatabaseReference

    private var rootHeight = 0
    private var keyboardHeight = 0
    private var isEmoticonContainerShown = false

    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_chat,
            ConstraintLayout(this),
            false
        )
        setContentView(binding.root)

        val chatType = intent.getStringExtra(KeyManager.ChatType.toKey())!!
        val roomKey = intent.getLongExtra(KeyManager.Room.KEY, -1)

        supportActionBar?.hide()
        Slidr.attach(this)

        when (chatType) {
            KeyManager.ChatType.FRIENDS -> {
                val friend = userVm.users.find {
                    it.key == roomKey
                }!!
                initializeBinding(friend.toRoom())
            }
            KeyManager.ChatType.OPEN -> {
                val room = chatVm.rooms.find {
                    it.key == roomKey
                }!!
                initializeBinding(room)
            }
        }

        initializeReference(roomKey, chatType)
    }

    private fun initializeReference(key: Long, chatType: String) {
        databaseReference = database.child("chat/$chatType/$key")
        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Chat::class.java)!!
                chatVm.messagesMap[key]?.let { messagesCache ->
                    if (isNewMessage) {
                        updateMessage(key, message)
                    } else {
                        if (message.key == messagesCache.last().key) {
                            isNewMessage = true // 다음 메시지부터 받기
                        }
                    }
                } ?: run {
                    isNewMessage = true
                    updateMessage(key, message)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // todo: message edited
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

    private fun initializeBinding(room: Room) {
        binding.room = room
        binding.rvChat.adapter = adapter
        binding.ivBack.setOnClickListener { finish() }

        adapter.submit(chatVm.messagesMap[room.key] ?: listOf())

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
                val message = Chat(
                    key = Util.generateMessageKey(inputMessage, userVm.me.name!!),
                    message = inputMessage,
                    time = Date().time,
                    type = ChatType.CHAT,
                    attachment = null,
                    owner = userVm.me,
                    mention = mutableListOf(),
                    messageViewType = ChatViewType.NORMAL
                )
                binding.etInput.clear()
                databaseReference.push().setValue(message)
            }
        }
    }

    private fun updateMessage(key: Long, chat: Chat) {
        chatVm.messagesMap[key]?.add(chat) ?: run {
            chatVm.messagesMap[key] = mutableListOf(chat)
        }
        adapter.submit(chatVm.messagesMap[key]!!)
        binding.rvChat.toBottomScroll()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
