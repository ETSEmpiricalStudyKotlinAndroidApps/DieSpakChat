/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.widget.Toast
import me.sungbin.spakchat.annotation.ActivityContext
import me.sungbin.spakchat.ui.activity.ExceptionActivity

object ExceptionUtil {

    fun except(exception: Exception, @ActivityContext context: Context) {
        Thread {
            val message = exception.message
            val line = exception.stackTrace[0].lineNumber
            val content = "$message #$line"
            Looper.prepare()
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            val intent = Intent(context, ExceptionActivity::class.java)
            intent.putExtra("message", content)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Looper.loop()
        }.start()
    }
}
