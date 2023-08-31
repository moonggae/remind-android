package com.ccc.remind.presentation.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun OutlinedTextButton(
    modifier: Modifier = Modifier,
    text: String,
    contentPadding: PaddingValues = PaddingValues(vertical = 4.dp, horizontal = 12.dp),
    textStyle: TextStyle = RemindMaterialTheme.typography.regular_lg,
    containerColor: Color = RemindMaterialTheme.colorScheme.bg_default,
    contentColor: Color = RemindMaterialTheme.colorScheme.accent_default,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(
            1.dp,
            color = contentColor
        ),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = textStyle,
            color = contentColor
        )
    }
}