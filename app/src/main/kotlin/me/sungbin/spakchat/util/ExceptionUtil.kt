package me.sungbin.spakchat.util

import android.content.Context
import me.sungbin.androidutils.util.Logger
import me.sungbin.androidutils.util.ToastLength
import me.sungbin.androidutils.util.ToastType
import me.sungbin.androidutils.util.ToastUtil
import me.sungbin.spakchat.ui.activity.ExceptionActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

object ExceptionUtil {
    fun except(exception: Exception, context: Context) {
        Logger.w(exception)
        val message = exception.localizedMessage
        val line = exception.stackTrace[0].lineNumber
        val content = "$message #$line"
        context.startActivity(context.intentFor<ExceptionActivity>("message" to content).newTask())
        ToastUtil.show(
            context,
            exception.toString(),
            ToastLength.SHORT,
            ToastType.ERROR
        )
    }
}