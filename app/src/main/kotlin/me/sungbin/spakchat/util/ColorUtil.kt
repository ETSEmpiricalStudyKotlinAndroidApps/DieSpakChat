package me.sungbin.spakchat.util

object ColorUtil {

    val randomColor get() = (Math.random() * 16777215).toInt() or (0xFF shl 24)

}