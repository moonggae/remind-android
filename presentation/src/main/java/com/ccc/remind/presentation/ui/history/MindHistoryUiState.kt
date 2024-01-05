package com.ccc.remind.presentation.ui.history

import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.setting.HistoryViewType
import com.ccc.remind.domain.entity.user.CurrentUser
import com.ccc.remind.domain.entity.user.User
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

data class MindHistoryUiState(
    val isLastPage: Boolean = false,
    val posts: List<MindPost> = emptyList(),
    val viewType: HistoryViewType = HistoryViewType.CALENDAR,
    val selectedDay: CalendarDay = CalendarDay(LocalDate.now(), DayPosition.MonthDate),
    val friend: User? = null,
    val currentUser: CurrentUser? = null
) {
    val selectedDayPostMinds: List<MindPost> get() = this.posts.filter {
        selectedDay.date == it.createdAt.toLocalDate()
    }

    val myLastPost: MindPost? get() = this.posts
        .filter { it.user?.id == currentUser?.uuid }
        .maxByOrNull { it.createdAt }

    val friendLastPost: MindPost? get() = this.posts
        .filter { it.user?.id == friend?.id }
        .maxByOrNull { it.createdAt }
}