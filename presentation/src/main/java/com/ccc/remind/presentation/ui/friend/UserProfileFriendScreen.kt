package com.ccc.remind.presentation.ui.friend

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileView
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileViewModel

@Composable
fun UserProfileFriendScreen(
    navController: NavController,
    viewModel: UserProfileViewModel,
    userId: String?
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(userId) {
        viewModel.setUser(userId ?: "")
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
        UserProfileView(
            user = uiState.user,
            modifier = Modifier.fillMaxWidth()
        )
    }
}