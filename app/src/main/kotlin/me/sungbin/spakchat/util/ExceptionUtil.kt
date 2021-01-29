/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

import android.app.Activity
import android.content.Context
import me.sungbin.androidutils.extensions.startActivity
import me.sungbin.androidutils.util.Logger
import me.sungbin.androidutils.util.toastutil.ToastUtil
import me.sungbin.spakchat.ui.activity.ExceptionActivity

object ExceptionUtil {

    fun except(exception: Exception, context: Context) {
        Logger.w(exception)
        val message = exception.localizedMessage
        val line = exception.stackTrace[0].lineNumber
        val content = "$message #$line"
        (context as Activity).startActivity<ExceptionActivity>(false, "message" to content)
        ToastUtil.show(context, exception.toString())
    }
}
