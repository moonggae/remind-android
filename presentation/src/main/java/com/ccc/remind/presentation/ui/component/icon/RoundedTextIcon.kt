package com.ccc.remind.presentation.ui.component.icon

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun RoundedTextIcon(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = RemindMaterialTheme.typography.bold_lg,
    color: Color = RemindMaterialTheme.colorScheme.fg_muted,
    containerColor: Color = RemindMaterialTheme.colorScheme.accent_bg,
    showBorder: Boolean = false
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(size = 24.dp),
        modifier = modifier.then(
            if(showBorder) {
                Modifier.border(
                    width = 1.dp,
                    color = color,
                    shape = RoundedCornerShape(size = 24.dp)
                )
            } else {
                Modifier
            }
        )
    ) {
        Text(
            text = text,
            style = style,
            color = color,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 6.dp
                )
        )
    }
}