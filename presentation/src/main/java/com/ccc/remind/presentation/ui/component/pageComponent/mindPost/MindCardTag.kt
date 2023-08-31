package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun MindCardTag(
    modifier: Modifier = Modifier,
    text: String,
) = Box(
    modifier = Modifier
        .border(
            width = 1.dp,
            color = RemindMaterialTheme.colorScheme.accent_default,
            shape = RoundedCornerShape(20.dp)
        )
        .background(
            color = RemindMaterialTheme.colorScheme.bg_default,
            shape = RoundedCornerShape(20.dp)
        )
        .padding(
            vertical = 2.dp,
            horizontal = 10.dp
        )
        .then(modifier)
) {
    Text(
        text = text,
        style = RemindMaterialTheme.typography.bold_md,
        color = RemindMaterialTheme.colorScheme.accent_default
    )
}