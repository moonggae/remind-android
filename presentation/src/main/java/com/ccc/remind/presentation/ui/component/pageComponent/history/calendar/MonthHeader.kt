package com.ccc.remind.presentation.ui.component.pageComponent.history.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.kizitonwose.calendar.core.CalendarMonth

@Composable
fun MonthHeader(
    month: CalendarMonth,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClickPrev) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
        }

        Text(
            text = "${month.yearMonth.year}${stringResource(R.string.year)} " +
                    "${month.yearMonth.month.value}${stringResource(R.string.month)}",
            style = RemindMaterialTheme.typography.bold_xl
        )

        IconButton(onClick = onClickNext) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_light),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}