package com.ccc.remind.presentation.ui.friend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.layout.fadingEdges
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserProfileCard
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserRelation
import com.ccc.remind.presentation.ui.component.text.SecondaryText
import com.ccc.remind.presentation.ui.friend.invite.InviteRequestListView
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import kotlinx.coroutines.launch

@Composable
fun FriendListScreen(
    navController: NavController,
    viewModel: FriendViewModel,
    sharedViewModel: SharedViewModel
) {
    val sharedUiState by sharedViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    // todo : base viewmodel addOn route 추가 후 start 이벤트에 넣기
    LaunchedEffect(navController.currentBackStackEntry) {
        if(navController.currentDestination?.route == Route.Friend.name) {
            viewModel.initRequestList()
        }
    }

    val screenScrollState = rememberScrollState()

    BasicScreen(
        viewModel = viewModel,
        appBar = {
            AppBar(
                title = stringResource(R.string.friend_list_appbar_title),
                navController = navController
            )
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(screenScrollState)
                .fadingEdges(screenScrollState)
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.friend_list_label_my_friend),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_default,
            )

            Spacer(modifier = Modifier.height(8.dp))

            if(sharedUiState.friend == null) {
                SecondaryText(text = stringResource(R.string.friend_list_no_friend_text))
            } else {
                sharedUiState.friend?.let { friend ->
                    UserProfileCard(
                        user = friend,
                        showTextSuffix = false,
                        navController = navController,
                        relation = UserRelation.FRIEND
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            InviteRequestListView(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel
            )
        }

        PrimaryButton(
            text = stringResource(R.string.friend_list_invite_friend_button_label),
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
            onClick = { scope.launch { navController.navigate(Route.Invite.name) } },
            enabled = sharedUiState.friend == null
        )
    }
}