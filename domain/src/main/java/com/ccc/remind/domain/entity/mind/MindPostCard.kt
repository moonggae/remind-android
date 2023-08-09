package com.ccc.remind.domain.entity.mind

data class MindPostCard(
    val id: Int,
    val type: MindCardSelectType,
    val card: MindCard
)