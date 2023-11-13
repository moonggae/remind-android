package com.ccc.remind.domain.entity.mind

import com.ccc.remind.domain.entity.user.User
import java.time.ZonedDateTime

data class MindLike(
    val id: Int,
    val createdAt: ZonedDateTime,
    val user: User
)
