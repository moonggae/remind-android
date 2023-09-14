package com.ccc.remind.presentation.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.OutlinedTextButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.user.MenuButton
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserProfileCard
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun UserScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val sharedUiState by sharedViewModel.uiState.collectAsState()

    BasicScreen(
        appBar = { AppBar(title = stringResource(R.string.user_appbar_title)) }
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            UserProfileCard(
                profileImageUrl = sharedUiState.user?.profileImage?.url,
                displayName = sharedUiState.user?.displayName ?: stringResource(R.string.empty_user_display_name)
            )

            OutlinedTextButton(
                text = stringResource(R.string.user_profile_management_button),
                textStyle = RemindMaterialTheme.typography.regular_sm,
                contentColor = RemindMaterialTheme.colorScheme.fg_muted,
                containerColor = RemindMaterialTheme.colorScheme.bg_default,
                modifier = Modifier
                    .width(79.dp)
                    .height(24.dp),
                contentPadding = PaddingValues()
            ) {
                navController.navigate(Route.Main.User.ProfileEdit.name)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(RemindMaterialTheme.colorScheme.bg_subtle)
                .padding(vertical = 8.dp)
        ) {
            MenuButton(text = stringResource(R.string.user_menu_button_setting)) { /* TODO */ }
            MenuButton(text = stringResource(R.string.user_menu_button_qna)) { /* TODO */ }
            MenuButton(text = stringResource(R.string.user_menu_button_user_terms)) { /* TODO */ }
            MenuButton(text = stringResource(R.string.user_menu_button_personal_information_terms)) { /* TODO */ }
        }

        Spacer(modifier = Modifier.weight(1f))

        LogoutButton(
            modifier = Modifier.align(CenterHorizontally)
        ) {

        }
    }
}


@Composable
private fun LogoutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = RemindMaterialTheme.colorScheme.fg_default,
            containerColor = Color.Transparent
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.to_logout),
            style = RemindMaterialTheme.typography.regular_lg
        )
    }
}