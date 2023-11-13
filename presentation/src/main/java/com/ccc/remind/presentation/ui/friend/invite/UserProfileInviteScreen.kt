package com.ccc.remind.presentation.ui.friend.invite

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
    val inviteUiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(inviteUiState.user?.id) {
        scope.launch {
            viewModel.setUser(inviteUiState.user)
        }
    }

    BasicScreen(
        appBar = {
            AppBar(
                title = "초대하기",
                navController = navController
            )
        }
    ) {
        UserProfileView(
            user = uiState.user,
            modifier = Modifier.fillMaxWidth()
        )

        uiState.user?.let {
            PrimaryButton(text = stringResource(id = R.string.invite_profile_invite_button)) {
                scope.launch {
                    inviteViewModel.submitRequestFriend()
                    inviteViewModel.removeInviteCode()
                    navController.popBackStack()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}