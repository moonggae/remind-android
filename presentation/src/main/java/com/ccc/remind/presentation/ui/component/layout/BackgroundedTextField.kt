package com.ccc.remind.presentation.ui.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun BackgroundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    contentAlignment: Alignment = Alignment.CenterStart,
    textStyle: TextStyle = RemindMaterialTheme.typography.regular_lg
        .copy(color = RemindMaterialTheme.colorScheme.fg_default),
    suffix: (@Composable () -> Unit)? = null,
    padding: PaddingValues = PaddingValues(horizontal = 22.dp),
    singleLine: Boolean = true
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(RemindMaterialTheme.colorScheme.bg_subtle)
                .height(50.dp)
                .fillMaxWidth()
                .padding(padding),
            contentAlignment = contentAlignment
        ) {
            it()
            if(suffix != null) {
                Box(modifier = Modifier.align(Alignment.CenterEnd)) {suffix()}
            }
        }
    }
}