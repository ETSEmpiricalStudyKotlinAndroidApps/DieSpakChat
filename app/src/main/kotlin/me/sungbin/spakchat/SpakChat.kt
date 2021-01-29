/*
 * Create by Sungbin Ji on 2021. 1. 29.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 */

package me.sungbin.spakchat

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpakChat : Application() {

    companion object {
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
