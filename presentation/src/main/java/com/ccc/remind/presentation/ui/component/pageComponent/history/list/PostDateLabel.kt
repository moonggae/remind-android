package com.ccc.remind.presentation.ui.component.pageComponent.history.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.toFormatString
import java.time.ZonedDateTime

@Composable
fun PostDateLabel(
    createdAt: ZonedDateTime,
    style: TextStyle = RemindMaterialTheme.typography.regular_md,
    color: Color = RemindMaterialTheme.colorScheme.fg_default,
    modifier: Modifier = Modifier
) {
    Text(
        text = createdAt.toFormatString("yyyy년 M월 d일"),
        style = style,
        color = color,
        modifier = modifier
    )
}