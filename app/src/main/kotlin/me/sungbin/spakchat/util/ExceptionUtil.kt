/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

import android.content.Context
import android.os.Looper
import android.widget.Toast
import me.sungbin.androidutils.util.Logger
import me.sungbin.spakchat.annotation.ActivityContext
import kotlin.system.exitProcess

object ExceptionUtil {

    fun except(exception: Exception, @ActivityContext context: Context) {
        Thread {
            val message = exception.message
            val line = exception.stackTrace[0].lineNumber
            val content = "예상치 못한 오류가 발생했어요 :(\n\n$message #$line"
            Looper.prepare()
            Logger.e(message)
            Toast.makeText(context, content, Toast.LENGTH_LONG).show()
            Looper.loop()
            exitProcess(10)
        }.start()
    }
}
