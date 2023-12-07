package com.ccc.remind.presentation.ui.user

import android.app.Activity
import android.content.Intent
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.domain.entity.user.toUser
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.OutlinedTextButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.user.MenuButton
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserProfileCard
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserRelation
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.coroutines.launch

@Composable
fun UserScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
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
            sharedUiState.currentUser?.let { user ->
                UserProfileCard(
                    user = user.toUser(),
                    navController = navController,
                    relation = UserRelation.ME
                )
            }

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
            MenuButton(text = stringResource(R.string.user_menu_button_setting)) {
                scope.launch { navController.navigate(Route.Setting.name) }
            }
            MenuButton(text = stringResource(R.string.user_menu_button_friend)) {
                scope.launch { navController.navigate(Route.Friend.name) }
            }
            val openLicenseString = stringResource(R.string.user_menu_button_open_source_licenses)
            MenuButton(text = openLicenseString) {
                val activity = context as Activity
                OssLicensesMenuActivity.setActivityTitle(openLicenseString)
                activity.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }
//            MenuButton(text = stringResource(R.string.user_menu_button_user_terms)) { /* TODO */ }
//            MenuButton(text = stringResource(R.string.user_menu_button_personal_information_terms)) { /* TODO */ }
        }

        Spacer(modifier = Modifier.weight(1f))

        LogoutButton(
            modifier = Modifier.align(CenterHorizontally),
            onClick = sharedViewModel::logout
        )

        Spacer(modifier = Modifier.height(32.dp))
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