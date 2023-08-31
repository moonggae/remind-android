package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun MindMemoTextField(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = RemindMaterialTheme.colorScheme.bg_muted,
            unfocusedContainerColor = RemindMaterialTheme.colorScheme.bg_muted,
            disabledContainerColor = RemindMaterialTheme.colorScheme.bg_muted,
            focusedPlaceholderColor = RemindMaterialTheme.colorScheme.fg_subtle,
            unfocusedPlaceholderColor = RemindMaterialTheme.colorScheme.fg_subtle,
            focusedTextColor = RemindMaterialTheme.colorScheme.fg_muted,
            disabledTextColor = RemindMaterialTheme.colorScheme.fg_muted,
            unfocusedSupportingTextColor = RemindMaterialTheme.colorScheme.fg_muted,
            disabledSupportingTextColor = RemindMaterialTheme.colorScheme.fg_muted,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = RemindMaterialTheme.colorScheme.fg_muted,
        ),
        singleLine = false,
        placeholder = {
            Text(
                text = stringResource(R.string.mind_post_edit_placeholder_memo),
                style = RemindMaterialTheme.typography.regular_lg
            )
        },
        enabled = enabled,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 170.dp)
                .height(IntrinsicSize.Min)
        )
    )
}