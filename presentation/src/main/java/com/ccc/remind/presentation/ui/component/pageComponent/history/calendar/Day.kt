package com.ccc.remind.presentation.ui.component.pageComponent.history.calendar

import androidx.compose.foundation.background
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
import com.ccc.remind.presentation.ui.component.model.CalendarDayType
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
        isToday && isSelected -> CalendarDayType.SELECTED_TODAY
        isSelected -> CalendarDayType.SELECTED
        hasPost && isToday -> CalendarDayType.HAS_POST_TODAY
        isToday -> CalendarDayType.EMPTY_TODAY
        hasPost -> CalendarDayType.HAS_POST
        else -> CalendarDayType.EMPTY
    }

    val backgroundColor = when(dayType) {
        CalendarDayType.OUT_RANGE,
        CalendarDayType.EMPTY_TODAY,
        CalendarDayType.EMPTY -> Color.Transparent
        CalendarDayType.HAS_POST_TODAY,
        CalendarDayType.HAS_POST -> RemindMaterialTheme.colorScheme.accent_bg
        CalendarDayType.SELECTED_TODAY,
        CalendarDayType.SELECTED -> RemindMaterialTheme.colorScheme.accent_default
    }

    val textColor = when(dayType) {
        CalendarDayType.HAS_POST_TODAY,
        CalendarDayType.EMPTY_TODAY -> RemindMaterialTheme.colorScheme.accent_default
        CalendarDayType.SELECTED_TODAY -> RemindMaterialTheme.colorScheme.bg_default
        CalendarDayType.OUT_RANGE -> RemindMaterialTheme.colorScheme.fg_subtle
        CalendarDayType.HAS_POST,
        CalendarDayType.SELECTED,
        CalendarDayType.EMPTY -> RemindMaterialTheme.colorScheme.fg_default
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f) // This is important for square sizing!
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable { onClick(day) }
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = if (isToday) RemindMaterialTheme.typography.bold_md else RemindMaterialTheme.typography.regular_md
        )
    }
}