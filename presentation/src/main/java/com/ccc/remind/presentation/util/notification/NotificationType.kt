package com.ccc.remind.presentation.util.notification

enum class NotificationType(private val typeName: String) {
    MEMO_POST("MEMO.POST"),
    MIND_POST("MIND.POST"),
    MIND_REQUEST("MIND.REQUEST"),
    MEMO_UPDATE("MEMO.UPDATE"),
    MEMO_COMMENT("MEMO.COMMENT"),
    FRIEND_REQUEST("FRIEND.REQUEST"),
    FRIEND_ACCEPT("FRIEND.ACCEPT");

    companion object {
        operator fun invoke(typeName: String): NotificationType? {
            return NotificationType.values().find {
                it.typeName == typeName
            }
        }
    }
}