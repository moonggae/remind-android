package com.ccc.remind.presentation.ui.component.pageComponent.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.icon.UserProfileIcon
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.domain.url
import kotlinx.coroutines.launch

enum class UserRelation {
    ME,
    FRIEND,
    REQUEST_FRIEND,
    OTHER
}

@Composable
fun UserProfileCard(
    user: User,
    showTextSuffix: Boolean = true,
    relation: UserRelation,
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.clickable {
            scope.launch {
                val destination: String = when (relation) {
                    UserRelation.FRIEND -> "${Route.UserProfile.Friend.name}?id=${user.id}"
                    UserRelation.REQUEST_FRIEND -> Route.UserProfile.Invite.name
                    UserRelation.OTHER,
                    UserRelation.ME -> "${Route.UserProfile.Default.name}?id=${user.id}"
                }
                navController.navigate(destination)
            }
        }
    ) {
        UserProfileIcon(imageUrl = user.profileImage?.url)

        Text(
            text =
            if(showTextSuffix)
                stringResource(R.string.user_display_name_suffix, user.displayName ?: "")
            else
                user.displayName ?: "",
            style = RemindMaterialTheme.typography.label_medium,
            color = RemindMaterialTheme.colorScheme.fg_default
        )
    }
}