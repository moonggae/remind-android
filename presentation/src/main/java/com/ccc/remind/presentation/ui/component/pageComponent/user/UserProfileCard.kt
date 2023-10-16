package com.ccc.remind.presentation.ui.component.pageComponent.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.icon.UserProfileIcon
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun UserProfileCard(
    profileImageUrl: String? = null,
    displayName: String,
    showTextSuffix: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        UserProfileIcon(imageUrl = profileImageUrl)

        Text(
            text = if(showTextSuffix) stringResource(R.string.user_display_name_suffix, displayName) else displayName,
            style = RemindMaterialTheme.typography.label_medium,
            color = RemindMaterialTheme.colorScheme.fg_default
        )
    }
}