package me.sungbin.spakchat.model


/**
 * Created by SungBin on 2020-09-11.
 */

sealed class AccountStatus {
    object BAN: AccountStatus()
    object VERIFIED: AccountStatus()
    object NORMAL: AccountStatus()
}