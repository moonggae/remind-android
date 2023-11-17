package com.ccc.remind.presentation.ui.friend

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.button.MenuButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.dialog.AlertDialogManager
import com.ccc.remind.presentation.ui.component.dialog.MenuBottomSheetManager
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.model.ButtonModel
import com.ccc.remind.presentation.ui.component.model.ButtonPriority
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileView
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun UserProfileFriendScreen(
    navController: NavController,
    viewModel: UserProfileViewModel,
    friendViewModel: FriendViewModel,
    userId: String?
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(userId) {
        viewModel.setUser(userId ?: "")
    }

    val deleteAlertDialog = remember { AlertDialogManager(scope) }
    val menu = remember { MenuBottomSheetManager(scope) }

    menu.init(
        useDefaultCancelButton = true,
        buttons = listOf(ButtonModel(
            priority = ButtonPriority.WARN,
            textResId = R.string.to_delete,
            onClick = { deleteAlertDialog.open() }
        ))
    )

    deleteAlertDialog.init(
        useDefaultCancelButton = true,
        titleResId = R.string.user_profile_friend_delete_alert_title,
        contentResId = R.string.user_profile_friend_delete_alert_message,
        buttons = { manager ->
            listOf(
                ButtonModel(
                    textResId = R.string.to_delete,
                    priority = ButtonPriority.WARN,
                    onClick = {
                        scope.launch {
                            friendViewModel.submitDeleteFriend()
                            manager.close()
                            menu.close()
                            navController.popBackStack()
                        }
                    }
                )
            )
        }
    )

    BasicScreen(
        viewModel = viewModel,
        appBar = {
            AppBar(
                title = stringResource(R.string.user_profile_friend_appbar_title),
                navController = navController
            ) {
                MenuButton { menu.open() }
            }
        }
    ) {
        UserProfileView(
            user = uiState.user,
            modifier = Modifier.fillMaxWidth()
        )
    }

    menu.instance()
    deleteAlertDialog.instance()
}