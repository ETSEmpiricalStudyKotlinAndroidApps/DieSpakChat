package me.sungbin.spakchat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.sungbin.spakchat.util.ExceptionUtil


/**
 * Created by SungBin on 2020-09-11.
 */

@HiltAndroidApp
class SpakChat : Application() {

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            ExceptionUtil.except(Exception(throwable), applicationContext)
        }

    }

}