package com.ccc.remind.presentation.ui.history

import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.setting.HistoryViewType
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

data class MindHistoryUiState(
    val isLastPage: Boolean = false,
    val postMinds: List<MindPost> = emptyList(),
    val viewType: HistoryViewType = HistoryViewType.CALENDAR,
    val selectedDay: CalendarDay = CalendarDay(LocalDate.now(), DayPosition.MonthDate)
) {
    val selectedDayPostMinds: List<MindPost> get() = postMinds.filter {
        selectedDay.date == it.createdAt.toLocalDate()
    }
}