/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

import android.content.Context
import me.sungbin.androidutils.extensions.join

object Util {

    fun generateRandomKey(key: String, value: String): Long {
        val strings = key + value
        val randomChars = mutableListOf<Int>().apply {
            repeat(10) {
                add(strings.random().toInt())
            }
        }
        return randomChars.shuffled().join("").toLong()
    }

    @Suppress("DEPRECATION")
    fun getVersionCode(context: Context) =
        context.packageManager.getPackageInfo(context.packageName, 0).versionCode.toLong()
}
