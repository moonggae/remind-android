package com.ccc.remind.data.source.remote.model.mind.vo

import com.ccc.remind.data.source.remote.model.user.UserVO
import java.time.ZonedDateTime
import java.util.UUID

data class MindCommentVO(
    val id: Int,
    val createdAt: ZonedDateTime,
    val text: String,
    val user: UserVO,
    val likes: List<MindLikeVO>
) {
    fun isMine(userId: UUID): Boolean {
        return user.id == userId
    }
}