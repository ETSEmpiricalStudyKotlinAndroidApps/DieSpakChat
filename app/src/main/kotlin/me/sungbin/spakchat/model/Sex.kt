package me.sungbin.spakchat.model


/**
 * Created by SungBin on 2020-09-11.
 */

sealed class Sex {
    object MEN: Sex()
    object WOMEN: Sex()
}