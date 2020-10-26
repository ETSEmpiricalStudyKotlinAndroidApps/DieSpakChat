package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sungbin.androidutils.util.Logger
import me.sungbin.spakchat.R
import me.sungbin.spakchat.ui.activity.MainActivity

/**
 * Created by SungBin on 2020-09-10.
 */

class ContactFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_contact, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.w("AAAAAA")

        db.user.value?.values?.map {
            Logger.w(it.name)
        }

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_contact)
            show()
        }
    }
}