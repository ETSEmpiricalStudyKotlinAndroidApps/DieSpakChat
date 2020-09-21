package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.sungbintool.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.R
import me.sungbin.spakchat.util.SettingUtil


/**
 * Created by SungBin on 2020-09-19.
 */

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        Logger.w("AAA", SettingUtil.read("aaa"))
    }

}