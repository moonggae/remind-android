package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ccc.remind.R
import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun HomeTitleText(page: Int, myDisplayName: String?, friend: UserProfile?) {
    val titleText = if(page == 0)
        stringResource(
            id = R.string.home_title_my_view,
            myDisplayName ?: stringResource(R.string.empty_user_display_name)
        )
    else {
        if(friend == null)
            stringResource(R.string.home_title_other_view_empty_user)
        else
            stringResource(
                id = R.string.home_title_other_user_view,
                friend.displayName ?: stringResource(R.string.empty_user_display_name)
            )
    }

    Text(
        titleText,
        style = RemindMaterialTheme.typography.bold_xxl
    )
}