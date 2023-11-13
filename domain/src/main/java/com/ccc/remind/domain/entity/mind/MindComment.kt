package com.ccc.remind.domain.entity.mind

import com.ccc.remind.domain.entity.user.User
import java.time.ZonedDateTime

data class MindComment(
    val id: Int,
    val createdAt: ZonedDateTime,
    val text: String,
    val user: User,
    val likes: List<MindLike>
)
