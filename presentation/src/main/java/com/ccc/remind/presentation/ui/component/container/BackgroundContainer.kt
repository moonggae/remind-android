package com.ccc.remind.presentation.ui.component.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun BackgroundContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .background(
            color = RemindMaterialTheme.colorScheme.bg_muted,
            shape = RoundedCornerShape(size = 20.dp)
        )
        .then(modifier),
    contentAlignment = Alignment.Center
) {
    content()
}