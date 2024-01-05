package com.ccc.remind.presentation.ui.history

import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.setting.HistoryViewType
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate
import java.util.UUID

data class MindHistoryUiState(
    val isLastPage: Boolean = false,
    val posts: List<MindPost> = emptyList(),
    val viewType: HistoryViewType = HistoryViewType.CALENDAR,
    val selectedDay: CalendarDay = CalendarDay(LocalDate.now(), DayPosition.MonthDate)
) {
    val selectedDayPostMinds: List<MindPost> get() = posts.filter {
        selectedDay.date == it.createdAt.toLocalDate()
    }

    fun getLastPostByUser(uuid: UUID): MindPost? = posts.find { it.user?.id == uuid }
}