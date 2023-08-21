package com.ccc.remind.domain.entity.mind

import com.ccc.remind.domain.entity.user.PostUser
import java.time.ZonedDateTime

data class MindComment(
    val id: Int,
    val createdAt: ZonedDateTime,
    val text: String,
    val user: PostUser,
    val likes: List<MindLike>
)
