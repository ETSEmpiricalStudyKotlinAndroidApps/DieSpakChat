/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved. 
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE : https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.util

import me.sungbin.spakchat.gmail.GmailSender
import me.sungbin.spakchat.secret.Gmail

object Gmail {

    private val gmailSender = GmailSender(Gmail.ID, Gmail.PASSWORD)

    fun send(address: String, title: String, content: String) {
        gmailSender.sendMail(title, content, address)
    }
}
