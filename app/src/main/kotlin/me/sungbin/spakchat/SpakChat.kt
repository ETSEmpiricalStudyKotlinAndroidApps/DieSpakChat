package me.sungbin.spakchat

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.HiltAndroidApp
import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.user.User


/**
 * Created by SungBin on 2020-09-11.
 */

@HiltAndroidApp
class SpakChat : Application() {

    companion object {
        val users: MutableLiveData<HashMap<String, User>> = MutableLiveData()
        val messages: MutableLiveData<HashMap<String, Message>> = MutableLiveData()
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        /*Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            ExceptionUtil.except(Exception(throwable), applicationContext)
        }*/

    }

}