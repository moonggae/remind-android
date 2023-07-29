package com.ccc.remind.data.source.remote.model.mind.vo

import java.time.ZonedDateTime

data class MindPostImageVO(
    val createdAt: ZonedDateTime,
    val id: Int,
    val image: ImageFileVO
)