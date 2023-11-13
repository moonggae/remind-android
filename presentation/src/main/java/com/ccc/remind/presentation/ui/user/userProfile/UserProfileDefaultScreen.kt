package com.ccc.remind.presentation.ui.user.userProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar

@Composable
fun UserProfileDefaultScreen(
    navController: NavController,
    viewModel: UserProfileViewModel,
    userId: String?
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.setUser(userId ?: "")
    }

    BasicScreen(
        appBar = {
            AppBar(
                title = "사용자 정보",
                navController = navController
            )
        },
        verticalArrangement = Arrangement.Center
    ) {
        UserProfileView(
            user = uiState.user,
            modifier = Modifier.fillMaxWidth()
        )
    }
}