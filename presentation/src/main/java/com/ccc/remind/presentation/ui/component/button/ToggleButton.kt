package com.ccc.remind.presentation.ui.component.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ccc.remind.presentation.ui.component.model.IToggleValue
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun <T : IToggleValue> ToggleButton(
    selectedValues: List<T>,
    item: T,
    onClick: (T) -> Unit
) {
    val isSelected = selectedValues.contains(item)

    OutlinedTextButton(
        text = when {
            item.value == null -> item.text
            item.value!! > 0 -> "${item.text} ↑"
            item.value!! < 0 -> "${item.text} ↓"
            else -> item.text
        },
        contentColor =
        if (isSelected) RemindMaterialTheme.colorScheme.bg_default
        else RemindMaterialTheme.colorScheme.accent_default,
        containerColor =
        if (isSelected) RemindMaterialTheme.colorScheme.accent_default
        else Color.Transparent,
        onClick = { onClick(item) }
    )
}