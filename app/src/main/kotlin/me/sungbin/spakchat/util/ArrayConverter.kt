/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

object ArrayConverter {

    const val PREFIX = "⭓"

    inline fun <reified T> toArray(value: String?): List<T> {
        val list = mutableListOf<T>()
        value?.split(PREFIX)?.forEach {
            if (it.isNotBlank()) { // todo: 과연 공백이 들어오는 데이터가 있을까?
                list.add(it as T)
            }
        }
        return list
    }

    fun <T> List<T>?.toText(): String {
        val string = StringBuilder()
        this?.forEach {
            val value = it.toString()
            if (value.isNotBlank()) { // todo: 과연 공백이 들어오는 데이터가 있을까?
                string.append(PREFIX + value)
            }
        }
        return string.toString()
    }
}
