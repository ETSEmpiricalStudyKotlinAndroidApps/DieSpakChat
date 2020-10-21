package me.sungbin.spakchat.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.user.User

/**
 * Created by SungBin on 2020-10-21.
 */

class DataBaseViewModel : ViewModel() {
    val user = MutableLiveData<HashMap<String, User>>()
    val message = MutableLiveData<HashMap<String, Message>>()
}
