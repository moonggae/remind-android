package com.ccc.remind.domain.entity.mind

data class MindPost(
    val id: Int,
    val cards: List<MindPostCard>,
    val images: List<ImageFile> = emptyList(),
    val memo: String?
)