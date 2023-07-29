package com.ccc.remind.presentation.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.component.model.IToggleValue
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun <T: IToggleValue> ToggleButton(
    selectedValues: List<T>,
    value: T,
    onClick: (T) -> Unit
) {
    val isSelected = selectedValues.contains(value)

    OutlinedButton(
        onClick = { onClick(value) },
        border = BorderStroke(
            1.dp,
            color = RemindMaterialTheme.colorScheme.accent_default
        ),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =
            if (isSelected) RemindMaterialTheme.colorScheme.accent_default
            else Color.Transparent
        ),
        contentPadding = PaddingValues(
            vertical = 4.dp,
            horizontal = 12.dp
        ),
        modifier = Modifier
            .padding(0.dp)
            .defaultMinSize(
                minWidth = 1.dp,
                minHeight = 1.dp
            )
    ) {
        Text(
            text = value.text,
            style = RemindMaterialTheme.typography.regular_lg,
            color =
            if (isSelected) RemindMaterialTheme.colorScheme.bg_default
            else RemindMaterialTheme.colorScheme.accent_default
        )
    }
}