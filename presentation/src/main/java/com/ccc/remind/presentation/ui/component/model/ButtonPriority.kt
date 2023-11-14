package com.ccc.remind.presentation.ui.component.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

enum class ButtonPriority {
    DEFAULT,
    WARN,
    ACCENT;

    @Composable
    fun getColor(): Color = when (this) {
        ButtonPriority.DEFAULT -> RemindMaterialTheme.colorScheme.fg_muted
        ButtonPriority.ACCENT -> RemindMaterialTheme.colorScheme.accent_default
        ButtonPriority.WARN -> RemindMaterialTheme.colorScheme.warn_onAccent
    }
}