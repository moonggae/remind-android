package com.ccc.remind.presentation.ui.component.pageComponent.history.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.toFormatString
import java.time.ZonedDateTime

@Composable
fun PostDateLabel(
    createdAt: ZonedDateTime,
    modifier: Modifier = Modifier
) {
    Text(
        text = createdAt.toFormatString("yyyy년 M월 d일"),
        style = RemindMaterialTheme.typography.regular_md,
        color = RemindMaterialTheme.colorScheme.fg_default,
        modifier = modifier
    )
}