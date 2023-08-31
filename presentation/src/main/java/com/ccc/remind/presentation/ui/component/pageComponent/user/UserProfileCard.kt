package com.ccc.remind.presentation.ui.component.pageComponent.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun UserProfileCard(
    picture: Painter? = null,
    displayName: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = picture ?: painterResource(id = R.drawable.ic_user),
            contentDescription = stringResource(R.string.user_icon),
            tint = RemindMaterialTheme.colorScheme.bg_muted,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(RemindMaterialTheme.colorScheme.fg_subtle)
                .padding(8.dp)
        )

        Text(
            text = stringResource(R.string.user_display_name_suffix, displayName),
            style = RemindMaterialTheme.typography.label_medium,
            color = RemindMaterialTheme.colorScheme.fg_default
        )
    }
}