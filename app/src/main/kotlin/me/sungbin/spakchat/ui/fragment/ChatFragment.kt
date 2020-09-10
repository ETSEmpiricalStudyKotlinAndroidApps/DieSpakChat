package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.test_fragment.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.ui.activity.MainActivity


/**
 * Created by SungBin on 2020-09-10.
 */

class ChatFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.test_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_chat)
            show()
        }
        tv_test.text = "ChatFragment"
    }
}