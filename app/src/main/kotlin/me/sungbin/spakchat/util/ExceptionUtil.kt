/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import me.sungbin.androidutils.util.Logger
import me.sungbin.androidutils.util.toastutil.ToastUtil
import me.sungbin.spakchat.ui.activity.ExceptionActivity
import kotlin.system.exitProcess

object ExceptionUtil {

    fun except(exception: Exception, context: Context) {
        (context as Activity).runOnUiThread {
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
        }
        exitProcess(10)
    }
}
