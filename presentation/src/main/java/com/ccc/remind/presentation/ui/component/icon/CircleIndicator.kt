package com.ccc.remind.presentation.ui.component.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun CircleIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    currentIndex: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(size) { index ->
            if (index == currentIndex)
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = RemindMaterialTheme.colorScheme.accent_default,
                            shape = CircleShape
                        )
                        .background(RemindMaterialTheme.colorScheme.accent_bg)
                        .size(10.dp)
                )
            else
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(RemindMaterialTheme.colorScheme.accent_bg)
                        .size(8.dp)
                )
        }
    }
}