package com.ccc.remind.presentation.ui.component.pageComponent.history.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    hasPost: Boolean = false,
    onClick: (day: CalendarDay) -> Unit
) {
    val isToday = remember { day.date == LocalDate.now() }
    val dayType: CalendarDayType = when {
        day.position != DayPosition.MonthDate -> CalendarDayType.OUT_RANGE
        isToday && isSelected && hasPost -> CalendarDayType.SELECTED_HAS_POST_TODAY
        isToday && isSelected -> CalendarDayType.SELECTED_EMPTY_TODAY
        isToday && hasPost -> CalendarDayType.HAS_POST_TODAY
        isSelected && hasPost -> CalendarDayType.SELECTED_HAS_POST
        isSelected -> CalendarDayType.SELECTED_EMPTY
        isToday -> CalendarDayType.EMPTY_TODAY
        hasPost -> CalendarDayType.HAS_POST
        else -> CalendarDayType.EMPTY
    }

    val backgroundColor = when(dayType) {
        CalendarDayType.OUT_RANGE,
        CalendarDayType.EMPTY_TODAY,
        CalendarDayType.SELECTED_EMPTY,
        CalendarDayType.SELECTED_EMPTY_TODAY,
        CalendarDayType.EMPTY -> Color.Transparent
        CalendarDayType.HAS_POST_TODAY,
        CalendarDayType.HAS_POST,
        CalendarDayType.SELECTED_HAS_POST_TODAY,
        CalendarDayType.SELECTED_HAS_POST -> RemindMaterialTheme.colorScheme.accent_bg
    }

    val textColor = when(dayType) {
        CalendarDayType.HAS_POST_TODAY,
        CalendarDayType.SELECTED_EMPTY_TODAY,
        CalendarDayType.SELECTED_HAS_POST_TODAY,
        CalendarDayType.EMPTY_TODAY -> RemindMaterialTheme.colorScheme.accent_default
        CalendarDayType.HAS_POST,
        CalendarDayType.SELECTED_HAS_POST,
        CalendarDayType.SELECTED_EMPTY,
        CalendarDayType.EMPTY -> RemindMaterialTheme.colorScheme.fg_default
        CalendarDayType.OUT_RANGE -> RemindMaterialTheme.colorScheme.fg_subtle
    }

    val borderColor = when(dayType) {
        CalendarDayType.SELECTED_EMPTY,
        CalendarDayType.SELECTED_HAS_POST_TODAY,
        CalendarDayType.SELECTED_EMPTY_TODAY,
        CalendarDayType.SELECTED_HAS_POST -> RemindMaterialTheme.colorScheme.accent_default
        else -> Color.Transparent
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f) // This is important for square sizing!
            .clip(CircleShape)
            .background(color = backgroundColor)
            .border(2.dp, borderColor, CircleShape)
            .clickable(dayType != CalendarDayType.OUT_RANGE) { onClick(day) }
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = if (isToday) RemindMaterialTheme.typography.bold_lg else RemindMaterialTheme.typography.regular_lg
        )
    }
}