package com.ccc.remind.presentation.ui.component.pageComponent.history.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.domain.entity.mind.MindPost
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth

@Composable
fun MindPostCalendarView(
    selectedDay: CalendarDay?,
    postMinds: List<MindPost>,
    onClickDay: (day: CalendarDay) -> Unit,
    onChangeMonth: (month: CalendarMonth) -> Unit
) {
    val scope = rememberCoroutineScope()

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY).first()
    )

    val visibleMonth = rememberFirstCompletelyVisibleMonth(state)
    LaunchedEffect(visibleMonth) {
        onChangeMonth(visibleMonth)
    }

    HorizontalCalendar(
        state = state,
        dayContent = { day ->
            Box(
                modifier = Modifier.padding(4.dp)
            ) {
                Day(
                    day = day,
                    isSelected = selectedDay == day,
                    hasPost = postMinds.any { day.date == it.createdAt.toLocalDate() },
                    onClick = onClickDay
                )
            }
        },
        monthHeader = { month ->
            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
            MonthHeader(
                month = month,
                onClickPrev = {
                    scope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                    }
                },
                onClickNext = {
                    scope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
        }
    )
}