package com.ccc.remind.presentation.ui.history

import com.ccc.remind.domain.entity.mind.MindPost
import com.kizitonwose.calendar.core.CalendarDay

data class MindHistoryUiState(
    val isLastPage: Boolean = false,
    val postMinds: List<MindPost> = emptyList(),
    val viewType: HistoryViewType = HistoryViewType.CALENDAR,
    val selectedDay: CalendarDay? = null
) {
    val selectedDayPostMinds: List<MindPost> get() {
        if(selectedDay == null) return emptyList()

        return postMinds.filter {
            selectedDay.date == it.createdAt.toLocalDate()
        }
    }
}