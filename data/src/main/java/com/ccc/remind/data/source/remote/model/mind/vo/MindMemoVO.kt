package com.ccc.remind.data.source.remote.model.mind.vo

import java.time.ZonedDateTime

data class MindMemoVO(
    val updatedAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val id: Int,
    val text: String,
    val comments: List<String>
)