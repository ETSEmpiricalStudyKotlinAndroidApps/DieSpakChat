package me.sungbin.spakchat.util

import me.sungbin.spakchat.model.message.Message
import me.sungbin.spakchat.model.message.MessageType
import me.sungbin.spakchat.model.user.AccountStatus
import me.sungbin.spakchat.model.user.Sex
import me.sungbin.spakchat.model.user.User
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.nextInt

object TestUtil {

    val getTestUser
        get() = User(
            id = Util.randomId,
            email = "sungbin.dev@gmail.com",
            password = "test-pw",
            name = "zmo__${Random.nextInt(0..10)}",
            profileImage = null,
            profileImageColor = ColorUtil.randomColor,
            backgroundImage = null,
            statusMessage = "싹쓰리",
            birthday = Date(),
            lastOnline = Date(),
            isOnline = Random.nextBoolean(),
            friends = listOf(),
            sex = Sex.WOMEN,
            emoji = listOf(),
            black = listOf(),
            accountStatus = AccountStatus.NORMAL,
            isTestMode = true
        )

    val getTestMessage
        get() = Message(
            id = Util.randomId,
            message = "싹쓰리",
            time = Date(),
            type = MessageType.CHAT,
            attachment = null,
            owner = getTestUser,
            mention = listOf(getTestUser),
            messageViewType = Random.nextInt(0, 3)
        )

    val getTestText get() = Util.randomId.substring(0..5)

    fun getTestUser(count: Int): List<User> {
        val users = ArrayList<User>()
        repeat(count) {
            users.add(getTestUser)
        }
        return users
    }

    fun getTestMessage(count: Int): List<Message> {
        val users = ArrayList<Message>()
        repeat(count) {
            users.add(getTestMessage)
        }
        return users
    }

}