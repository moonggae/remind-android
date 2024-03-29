package com.ccc.remind.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.icon.CircleIndicator
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.home.HomeTitleText
import com.ccc.remind.presentation.ui.component.pageComponent.home.NotificationIconButton
import com.ccc.remind.presentation.ui.history.MindHistoryViewModel
import com.ccc.remind.presentation.ui.notification.NotificationViewModel
import kotlinx.coroutines.launch

/* TODO
- request notification permission
*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    mindHistoryViewModel: MindHistoryViewModel
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val scope = rememberCoroutineScope()
    val notificationUiState by notificationViewModel.uiState.collectAsStateWithLifecycle()
    val sharedUiState by sharedViewModel.uiState.collectAsStateWithLifecycle()
    val mindHistoryUiState by mindHistoryViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
            notificationViewModel.initNotifications()
        }
    }

    val pagerState = rememberPagerState(
        initialPage = 0
    )

    BasicScreen(
        viewModel = notificationViewModel,
        appBar = {
            AppBar(
                navController = navController,
                title = "",
                enableBack = false,
                padding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp),
                suffix = {
                    NotificationIconButton(
                        isNotificationAlarmOn =  notificationUiState.isNotificationAlarmOn,
                        onClick = { scope.launch { navController.navigate(Route.NotificationList.name) } }
                    )
                }
            )
        },
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        HomeTitleText(
            page = pagerState.currentPage,
            myDisplayName = sharedUiState.currentUser?.displayName,
            friend = sharedUiState.friend
        )

        CircleIndicator(
            size = 2,
            currentIndex = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        HorizontalPager(
            pageCount = 2,
            state = pagerState
        ) { pageIndex ->
            when(pageIndex) {
                0 -> HomeMyView(
                    postMind = mindHistoryUiState.myLastPost,
                    displayName = sharedUiState.currentUser?.displayName,
                    navController = navController,
                )
                1 -> HomeOtherUserView(
                    postMind = mindHistoryUiState.friendLastPost,
                    displayName = sharedUiState.friend?.displayName,
                    navController = navController,
                    onRequestFriendMind = viewModel::submitRequestFriendMind
                )
            }
        }
    }
}