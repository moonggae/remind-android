package com.ccc.remind.presentation.ui.friend.invite

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileView
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun UserProfileInviteScreen(
    navController: NavController,
    viewModel: UserProfileViewModel,
    inviteViewModel: InviteViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val inviteUiState by inviteViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(inviteUiState.openedUser?.id) {
        scope.launch {
            viewModel.setUser(inviteUiState.openedUser)
        }
    }

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.invite_profile_appbar_title),
                navController = navController
            )
        }
    ) {
        UserProfileView(
            user = uiState.user,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )

        uiState.user?.let {
            PrimaryButton(
                text = stringResource(R.string.invite_profile_invite_button),
                modifier = Modifier.height(IntrinsicSize.Max)
            ) {
                scope.launch {
                    inviteViewModel.submitRequestFriend()
                    inviteViewModel.removeInviteCode()
                    navController.popBackStack(
                        route = Route.Invite.name,
                        inclusive = true,
                        saveState = false
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}