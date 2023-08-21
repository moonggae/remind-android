package com.ccc.remind.domain.entity.mind

import java.time.ZonedDateTime

data class MindMemo(
    val updatedAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val id: Int,
    val text: String,
    val comments: List<MindComment>
)