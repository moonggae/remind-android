package com.ccc.remind.data.source.remote.model.mind.vo

import com.ccc.remind.data.source.remote.model.user.UserVO
import java.time.ZonedDateTime

data class MindLikeVO(
    val id: Int,
    val createdAt: ZonedDateTime,
    val user: UserVO
)