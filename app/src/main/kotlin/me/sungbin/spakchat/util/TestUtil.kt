package me.sungbin.spakchat.util

import com.sungbin.sungbintool.util.Util
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

    val getTestId get() = Util.makeRandomUUID().replace("-", "").substring(0..8)

    val getTestUser
        get() = User(
            id = getTestId,
            email = "sungbin.dev@gmail.com",
            password = "test-pw",
            name = "zmo__${Random.nextInt(0..10)}",
            profileImage = null,
            backgroundImage = null,
            statusMessage = "싹쓰리",
            birthday = Date(),
            lastOnline = Date(),
            isOnline = Random.nextBoolean(),
            friends = listOf(),
            sex = Sex.WOMEN,
            emoji = listOf(),
            black = listOf(),
            accountStatus = AccountStatus.NORMAL
        )

    val getTestMessage
        get() = Message(
            id = getTestId,
            message = "싹쓰리",
            time = Date(),
            type = MessageType.CHAT,
            attachment = null,
            owner = getTestUser,
            mention = listOf(getTestUser)
        )

    val getTestText get() = getTestId.substring(0..5)

    fun getTestUser(count: Int): List<User> {
        val users = ArrayList<User>()
        for (i in 0..count) {
            users.add(getTestUser)
        }
        return users
    }

    fun getTestMessage(count: Int): List<Message> {
        val users = ArrayList<Message>()
        for (i in 0..count) {
            users.add(getTestMessage)
        }
        return users
    }

}