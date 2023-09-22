package com.ccc.remind.presentation.ui.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextButton(
    text: String,
    style: TextStyle = RemindMaterialTheme.typography.bold_lg,
    modifier: Modifier = Modifier,
    containerColor: Color = RemindMaterialTheme.colorScheme.accent_bg,
    contentColor: Color = RemindMaterialTheme.colorScheme.fg_default,
    disabledContainerColor: Color = RemindMaterialTheme.colorScheme.accent_onAccent,
    disabledContentColor: Color = RemindMaterialTheme.colorScheme.fg_subtle,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentEnforcement provides false
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor, disabledContentColor
            ),
            enabled = enabled,
            shape = shape,
            contentPadding = contentPadding,
            modifier = modifier.then(
                Modifier.height(48.dp)
            )
        ) {
            Text(
                text = text,
                style = style
            )
        }
    }
}