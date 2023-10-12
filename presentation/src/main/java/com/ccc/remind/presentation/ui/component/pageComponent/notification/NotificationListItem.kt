package com.ccc.remind.presentation.ui.component.pageComponent.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun NotificationListItem(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    timestamp: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        modifier = modifier.then(
            Modifier.padding(
                top = 14.dp,
                bottom = 25.dp,
                start = 23.dp,
                end = 23.dp
            )
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = RemindMaterialTheme.typography.regular_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            Text(
                text = timestamp,
                style = RemindMaterialTheme.typography.regular_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )
        }

        Text(
            text = text,
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_muted
        )
    }
}