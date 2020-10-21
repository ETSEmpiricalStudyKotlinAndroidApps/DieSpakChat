package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.sungbin.spakchat.R
import me.sungbin.spakchat.ui.activity.MainActivity


/**
 * Created by SungBin on 2020-09-10.
 */


class ChatFragment : BaseFragment() {

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

        /* rv_online.adapter = OnlineAdapter(TestUtil.getTestUser(10))
         rv_feed_chat.adapter = FeedChatAdapter(TestUtil.getTestMessage(10))
         rv_feed_chat.setFab(MainActivity.fabAction)*/

//        firestore.collection("users").document().get().addOnSuccessListener {
//            Logger.w(it?.toObject(User::class.java)?.id)
//        }
    }
}