/*
 * Create by Sungbin Ji on 2021. 1. 30.
 * Copyright (c) 2021. Sungbin Ji. All rights reserved.
 *
 * SpakChat license is under the MIT license.
 * SEE LICENSE: https://github.com/sungbin5304/SpakChat/blob/master/LICENSE
 */

package me.sungbin.spakchat.gmail

import me.sungbin.androidutils.util.Logger
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.OutputStream
import java.security.Security
import java.util.Properties
import javax.activation.DataHandler
import javax.activation.DataSource
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

// https://stackoverflow.com/a/2033124
class GmailSender(private val user: String, private val password: String) : Authenticator() {
    private val session: Session

    companion object {
        init {
            Security.addProvider(JSSEProvider())
        }
    }

    override fun getPasswordAuthentication() = PasswordAuthentication(user, password)

    @Synchronized
    @Throws(Exception::class)
    fun sendMail(subject: String?, body: String, address: String) {
        try {
            Thread {
                val message = MimeMessage(session)
                val handler = DataHandler(ByteArrayDataSource(body.toByteArray(), "text/plain"))
                message.sender = InternetAddress(user)
                message.subject = subject
                message.dataHandler = handler
                message.setRecipient(Message.RecipientType.TO, InternetAddress(address))
                Transport.send(message)
                Logger.w("메일 성공!")
            }.start()
        } catch (exception: Exception) {
            throw exception
        }
    }

    private class ByteArrayDataSource(private val data: ByteArray, private var type: String?) :
        DataSource {
        override fun getContentType() = if (type == null) {
            "application/octet-stream"
        } else {
            type.toString()
        }

        override fun getInputStream() = ByteArrayInputStream(data)
        override fun getName() = "ByteArrayDataSource"

        @Throws(IOException::class)
        override fun getOutputStream(): OutputStream {
            throw IOException("Not Supported")
        }
    }

    init {
        val props = Properties()
        props.setProperty("mail.transport.protocol", "smtp")
        props.setProperty("mail.host", "smtp.gmail.com")
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.socketFactory.fallback"] = "false"
        props.setProperty("mail.smtp.quitwait", "false")
        session = Session.getDefaultInstance(props, this)
    }
}
