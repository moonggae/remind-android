package com.ccc.remind.presentation.ui.history

import com.ccc.remind.domain.entity.mind.MindPost

data class MindHistoryUiState(
    val page: Int = 0,
    val lastPage: Int? = null,
    val postMinds: List<MindPost> = emptyList()
)