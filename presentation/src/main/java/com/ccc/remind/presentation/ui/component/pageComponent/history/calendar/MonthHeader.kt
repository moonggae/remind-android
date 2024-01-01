package com.ccc.remind.presentation.ui.component.pageComponent.history.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.kizitonwose.calendar.core.CalendarMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthHeader(
    month: CalendarMonth,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = RemindMaterialTheme.colorScheme.bg_muted,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            IconButton(onClick = onClickPrev) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = "",
                    tint = RemindMaterialTheme.colorScheme.fg_muted,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Text(
            text = "${month.yearMonth.year}${stringResource(R.string.year)} " +
                    "${month.yearMonth.month.value}${stringResource(R.string.month)}",
            style = RemindMaterialTheme.typography.bold_lg
        )

        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            IconButton(onClick = onClickNext) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_light),
                    contentDescription = "",
                    tint = RemindMaterialTheme.colorScheme.fg_muted,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}