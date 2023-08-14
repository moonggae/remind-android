package com.ccc.remind.presentation.ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFillButton(
    text: String,
    style: TextStyle = RemindMaterialTheme.typography.bold_xl,
    modifier: Modifier = Modifier,
    containerColor: Color = RemindMaterialTheme.colorScheme.bg_default,
    contentColor: Color = RemindMaterialTheme.colorScheme.fg_muted,
    onClick: () -> Unit
) {
    CompositionLocalProvider( // TextButton padding 제거
        LocalMinimumInteractiveComponentEnforcement provides false
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            shape = RectangleShape,
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .height(68.dp)
            )
        ) {
            Text(
                text = text,
                style = style
            )
        }
    }
}