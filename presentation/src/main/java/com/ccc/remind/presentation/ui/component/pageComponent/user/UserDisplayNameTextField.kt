package com.ccc.remind.presentation.ui.component.pageComponent.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun UserDisplayNameTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = RemindMaterialTheme.typography.regular_lg
            .copy(color = RemindMaterialTheme.colorScheme.fg_default),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(RemindMaterialTheme.colorScheme.bg_subtle)
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.CenterStart
        ) { it() }
    }
}