package com.ccc.remind.data.source.remote.model.mind.dto

data class MindPostRequestDto(
    val cards: List<MindPostCardRequest>,
    val images: List<String>,
    val memo: String
)