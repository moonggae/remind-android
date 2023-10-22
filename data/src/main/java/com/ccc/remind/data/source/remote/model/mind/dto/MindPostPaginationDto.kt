package com.ccc.remind.data.source.remote.model.mind.dto

data class MindPostPaginationDto(
    val page: Int,
    val lastPage: Int,
    val data: List<MindPostResponseDto>
)