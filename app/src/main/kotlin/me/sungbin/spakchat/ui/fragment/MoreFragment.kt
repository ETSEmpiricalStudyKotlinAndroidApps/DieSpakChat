package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_more.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.ui.activity.MainActivity

/**
 * Created by SungBin on 2020-09-10.
 */

class MoreFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_more, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabAction.hide()
        btn_clear_room.setOnClickListener {
            /*it.post {
                Thread {
                    userDb.clearAllTables()
                    ToastUtil.show(
                        requireContext(),
                        "room 초기화 완료",
                        ToastLength.SHORT,
                        ToastType.SUCCESS
                    )
                }.start()
            }*/
            userDb.clearAllTables()
        }
    }
}