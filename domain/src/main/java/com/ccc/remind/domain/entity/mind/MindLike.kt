package com.ccc.remind.domain.entity.mind

import com.ccc.remind.domain.entity.user.PostUser
import java.time.ZonedDateTime

data class MindLike(
    val id: Int,
    val createdAt: ZonedDateTime,
    val user: PostUser
)
