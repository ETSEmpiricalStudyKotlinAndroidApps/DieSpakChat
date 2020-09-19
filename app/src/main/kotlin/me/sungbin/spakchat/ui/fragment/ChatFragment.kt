package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sungbin.sungbintool.extensions.setFab
import kotlinx.android.synthetic.main.fragment_chat.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.adapter.FeedChatAdapter
import me.sungbin.spakchat.adapter.OnlineAdapter
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.util.TestUtil


/**
 * Created by SungBin on 2020-09-10.
 */

class ChatFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_chat, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_chat)
        }.show()

        rv_online.adapter = OnlineAdapter(TestUtil.getTestUser(10), requireActivity())
        rv_feed_chat.adapter = FeedChatAdapter(TestUtil.getTestMessage(10), requireActivity())
        rv_feed_chat.setFab(MainActivity.fabAction)
    }
}