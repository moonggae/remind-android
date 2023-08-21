package com.ccc.remind.presentation.ui.component.memo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun LinedText(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.then(
            Modifier.fillMaxWidth()
        )
    ) {
        Box(
            modifier = Modifier
                .background(RemindMaterialTheme.colorScheme.fg_subtle)
                .weight(1f, true)
                .height(0.5.dp)
        )

        content()

        Box(
            modifier = Modifier
                .background(RemindMaterialTheme.colorScheme.fg_subtle)
                .weight(1f, true)
                .height(0.5.dp)
        )
    }
}