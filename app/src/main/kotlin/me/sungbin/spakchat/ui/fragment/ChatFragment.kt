package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.sungbin.sungbintool.extensions.setFab
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.android.synthetic.main.fragment_chat.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.adapter.FeedChatAdapter
import me.sungbin.spakchat.adapter.OnlineAdapter
import me.sungbin.spakchat.ui.activity.MainActivity
import me.sungbin.spakchat.util.TestUtil
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by SungBin on 2020-09-10.
 */

@AndroidEntryPoint
@WithFragmentBindings
class ChatFragment : Fragment() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
    lateinit var storage: StorageReference

    @Inject
    @Named("database")
    lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_chat, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = false

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_chat)
        }.show()

        rv_online.adapter = OnlineAdapter(TestUtil.getTestUser(10), requireActivity())
        rv_feed_chat.adapter = FeedChatAdapter(TestUtil.getTestMessage(10), requireActivity())
        rv_feed_chat.setFab(MainActivity.fabAction)

    }
}