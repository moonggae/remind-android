package com.ccc.remind.presentation.ui.history

import com.ccc.remind.domain.entity.mind.MindPost

data class MindHistoryUiState(
    val isLastPage: Boolean = false,
    val postMinds: List<MindPost> = emptyList(),
    val viewType: HistoryViewType = HistoryViewType.CALENDAR
)