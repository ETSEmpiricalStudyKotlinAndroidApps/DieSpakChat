package me.sungbin.spakchat.model.message

/**
 * Created by SungBin on 2020-09-18.
 */

object MessageType {
    const val ATTACHMENT = 0
    const val REPLY = 1
    const val CHAT = 2
    const val EMOJI = 3
    const val GIFT = 4
    const val NOTICE = 5 // 일반 공지
    const val SHOUT = 6 // 외치기
    const val FEED = 7 // 카카오링크 같은거
    const val LOCATION = 8 // 실시간 위치공유
    const val VOICETALK = 9
    const val FACETALK = 10
    const val SPOILER = 11
    const val SECRET = 11 // 보려면 비밀번호 입력 필요
    const val GROUP = 12 // 묶인 메시지
}