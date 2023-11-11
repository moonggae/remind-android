package com.ccc.remind.presentation.ui.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun SecondaryText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        style = RemindMaterialTheme.typography.regular_lg,
        color = RemindMaterialTheme.colorScheme.fg_muted,
        modifier = modifier
    )
}