package com.ccc.remind.presentation.ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    textStyle: TextStyle = RemindMaterialTheme.typography.bold_xl.copy(color = RemindMaterialTheme.colorScheme.bg_default),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = RemindMaterialTheme.colorScheme.accent_default,
            disabledContainerColor = RemindMaterialTheme.colorScheme.button_disabled
        ),
        enabled = enabled,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}