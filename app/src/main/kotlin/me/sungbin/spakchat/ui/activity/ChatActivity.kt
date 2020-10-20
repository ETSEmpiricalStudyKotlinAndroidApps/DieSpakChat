package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.sungbin.sungbintool.extensions.clear
import com.sungbin.sungbintool.extensions.setTint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_chat.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.adapter.ChatAdapter
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.message.MessageType
import me.sungbin.spakchat.model.message.MessageViewType
import me.sungbin.spakchat.util.TestUtil
import me.sungbin.spakchat.util.Util
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()

        val id = intent.getStringExtra("id")

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
                    messageViewType = MessageViewType.OWN
                )
                messages.add(message)
                adapter.notifyDataSetChanged()
                rv_chat.scrollToPosition(adapter.itemCount - 1)
                et_input.clear()
                // database.child("chat/room/uuid").push().setValue(message)
            }
        }
    }

}