package com.ccc.remind.domain.entity.mind

import java.time.ZonedDateTime

data class MindPost(
    val id: Int,
    val createdAt: ZonedDateTime,
    val cards: List<MindPostCard>,
    val images: List<ImageFile> = emptyList(),
    val memo: MindMemo?
)