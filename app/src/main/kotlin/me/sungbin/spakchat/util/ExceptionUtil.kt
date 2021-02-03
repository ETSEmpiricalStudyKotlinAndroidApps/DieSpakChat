/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

import android.content.Context
import com.balsikandar.crashreporter.CrashReporter
import me.sungbin.androidutils.util.Logger
import kotlin.system.exitProcess

object ExceptionUtil {

    // todo: UncaughtException를 어떻게 해야하지?
    fun except(exception: Exception, context: Context) {
        /*(context as Activity).runOnUiThread {
            val message = exception.message
            val line = exception.stackTrace[0].lineNumber
            val content = "$message #$line"
            val intent = Intent(context, ExceptionActivity::class.java)
            intent.putExtra("message", content)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
            ToastUtil.show(context, exception.toString())
            Logger.e(message)
        }*/
        val message = exception.message
        val line = exception.stackTrace[0].lineNumber
        val content = "$message #$line"
        CrashReporter.logException(exception)
        exception.printStackTrace()
        Logger.e(content)
        exitProcess(10)
    }
}
