package me.sungbin.spakchat.util

import com.sungbin.sungbintool.util.Util

object Util {

    val randomId get() = Util.makeRandomUUID().replace("-", "").substring(0..8)

    fun generateMessageId(message: String, ownerName: String) =
        "${EncryptUtil.encrypt(EncryptUtil.EncryptType.MD5, message).substring(0..3)}${
            EncryptUtil.encrypt(
                EncryptUtil.EncryptType.SHA256,
                ownerName
            ).substring(0..3)
        }$randomId".substring(0..3)
}