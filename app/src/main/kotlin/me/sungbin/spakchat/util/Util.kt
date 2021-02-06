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
import kotlin.random.Random

object Util {

    private val randomId get() = Random.nextLong()

    // todo: WTF this function? should change UserKey generate algorithm.
    fun generateUserKey(id: String, password: String): Long {
        val idKey = id.first().toInt() * id.last().toInt()
        val passwordKey = password.first().toInt() + password.last().toInt()
        var key = (idKey * passwordKey).toString()
        if (key.length > 10) key = key.substring(0..10)
        return key.toLong()
    }

    // todo: WTF this function? should change MessageKey generate algorithm.
    fun generateMessageKey(message: String, ownerName: String): Long {
        val messageChars = mutableListOf<Char>().apply {
            if (message.length > 5) {
                repeat(Random.nextInt(3, message.length)) {
                    add(message.random())
                }
            } else {
                add(message.random())
            }
        }
        val ownerNameChars = mutableListOf<Char>().apply {
            if (ownerName.length > 5) {
                repeat(Random.nextInt(3, message.length)) {
                    add(message.random())
                }
            } else {
                add(message.random())
            }
        }
        return mutableListOf<Int>().apply {
            messageChars.forEach {
                add(it.toInt())
            }
            add(randomId.toString().substring(0..5).toInt())
            ownerNameChars.forEach {
                add(it.toInt())
            }
        }.shuffled().join("").substring(0..8).replace("[^0-9]+".toRegex(), "").toLong()
    }

    @Suppress("DEPRECATION")
    fun getVersionCode(context: Context) =
        context.packageManager.getPackageInfo(context.packageName, 0).versionCode.toLong()
}
