package com.ccc.remind.domain.entity.mind

data class MindPostList(
    val page: Int,
    val lastPage: Int,
    val data: List<MindPost>
)