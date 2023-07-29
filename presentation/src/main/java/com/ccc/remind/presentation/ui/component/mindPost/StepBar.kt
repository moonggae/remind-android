package com.ccc.remind.presentation.ui.component.mindPost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun StepBar(
    maxStep: Int,
    currentStep: Int
) {
    Row(
        modifier = Modifier.height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
    ) {
        for (i in 0 until maxStep) {
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .weight(1f)
                    .background(
                        if (i == currentStep) RemindMaterialTheme.colorScheme.accent_default
                        else RemindMaterialTheme.colorScheme.bg_subtle
                    )
            )
        }
    }
}