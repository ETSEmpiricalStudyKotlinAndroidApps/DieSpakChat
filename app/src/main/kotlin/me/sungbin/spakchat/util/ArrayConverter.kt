/*
 * Copyright (c) 2020. Ji Sungbin.
 */

package me.sungbin.spakchat.util

/**
 * Created by SungBin on 2020-10-28.
 */

const val PREFIX = "⭓"

inline fun <reified T> String?.toArray(): List<T> {
    val list = ArrayList<T>()
    this?.split(PREFIX)?.map {
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