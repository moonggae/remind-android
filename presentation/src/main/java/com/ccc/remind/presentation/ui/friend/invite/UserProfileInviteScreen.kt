package com.ccc.remind.presentation.ui.friend.invite

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.text.SecondaryText
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileView
import kotlinx.coroutines.launch

@Composable
fun UserProfileInviteScreen(
    navController: NavController,
    viewModel: InviteViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    BasicScreen(
        appBar = {
            AppBar(
                title = "초대하기",
                navController = navController
            )
        }
    ) {
        if (uiState.openedUserProfile == null) {
            SecondaryText(
                text = "사용자 정보를 불러올 수 없어요.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 120.dp)
            )
            return@BasicScreen
        }

        UserProfileView(userProfile = uiState.openedUserProfile!!)

        PrimaryButton(text = stringResource(id = R.string.invite_profile_invite_button)) {
            scope.launch {
                viewModel.submitRequestFriend()
                viewModel.removeInviteCode()
                navController.popBackStack()
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}