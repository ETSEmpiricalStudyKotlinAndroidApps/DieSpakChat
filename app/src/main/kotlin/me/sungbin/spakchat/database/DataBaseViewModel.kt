package me.sungbin.spakchat.database

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.androidutils.util.Logger
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.user.User

/**
 * Created by SungBin on 2020-10-21.
 */

class DataBaseViewModel : ViewModel() {
    val user = MutableLiveData<HashMap<String, User>>()
    val message = MutableLiveData<HashMap<String, Message>>()

    fun init(owner: LifecycleOwner) {
        user.observe(owner, {
            Logger.w(it)
        })
    }
}
