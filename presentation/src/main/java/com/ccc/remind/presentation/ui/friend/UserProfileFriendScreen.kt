package com.ccc.remind.presentation.ui.friend

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.text.SecondaryText
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileView
import kotlinx.coroutines.launch

@Composable
fun UserProfileFriendScreen(
    navController: NavController,
    viewModel: FriendViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    BackHandler(true) {
        scope.launch {
            viewModel.removeOpenedProfile()
        }
    }

    BasicScreen(
        viewModel = viewModel,
        appBar = {
            AppBar(
                title = "사용자 정보",
                navController = navController
            ) {
                // todo :  R.drawable.ic_meatball 아이콘 버튼으로 메뉴 펼친 후 삭제 하기 버튼 추가
                // primary button 삭제 버튼은 좋지 않아 보임
            }
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
    }
}