package com.ccc.remind.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.presentation.navigation.BottomNavigationBar
import com.ccc.remind.presentation.navigation.NavigationActions
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.navigation.friendGraph
import com.ccc.remind.presentation.navigation.inviteNavGraph
import com.ccc.remind.presentation.navigation.mainNavGraph
import com.ccc.remind.presentation.navigation.memoEditNavGraph
import com.ccc.remind.presentation.navigation.mindCardGraph
import com.ccc.remind.presentation.navigation.notificationGraph
import com.ccc.remind.presentation.navigation.postMindNavGraph
import com.ccc.remind.presentation.navigation.settingGraph
import com.ccc.remind.presentation.navigation.userProfileGraph
import com.ccc.remind.presentation.ui.card.CardViewModel
import com.ccc.remind.presentation.ui.friend.FriendViewModel
import com.ccc.remind.presentation.ui.friend.invite.InviteViewModel
import com.ccc.remind.presentation.ui.history.MindHistoryViewModel
import com.ccc.remind.presentation.ui.home.HomeViewModel
import com.ccc.remind.presentation.ui.memo.MemoEditViewModel
import com.ccc.remind.presentation.ui.mindPost.MindPostViewModel
import com.ccc.remind.presentation.ui.notification.NotificationViewModel
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileViewModel
import com.ccc.remind.presentation.util.Constants

/* TODO
- synchronize data using web socket
*/

@Preview
@Composable
fun AppPreview() {
    App()
}

@Composable
fun App(
    navController: NavController = rememberNavController(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    NavigationWrapper(
        navController = navController,
        sharedViewModel = sharedViewModel
    )
}

@Composable
private fun NavigationWrapper(
    navController: NavController = rememberNavController(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val navigationActions = remember(navController) {
        NavigationActions(navController as NavHostController)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination?.route

    val isNavigationBarVisible = destination?.startsWith(Route.Main.name) ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RemindMaterialTheme.colorScheme.bg_default)
    ) {
        val mindPostViewModel: MindPostViewModel = hiltViewModel()
        val mindHistoryViewModel: MindHistoryViewModel = hiltViewModel()
        val inviteViewModel: InviteViewModel = hiltViewModel()
        val memoEditViewModel: MemoEditViewModel = hiltViewModel()
        val homeViewModel: HomeViewModel = hiltViewModel()
        val notificationViewModel: NotificationViewModel = hiltViewModel()
        val cardViewModel: CardViewModel = hiltViewModel()
        val friendViewModel: FriendViewModel = hiltViewModel()
        val userProfileViewModel: UserProfileViewModel = hiltViewModel()

        NavHost(
            navController = navController as NavHostController,
            modifier = Modifier.weight(1f),
            startDestination = Constants.START_TOP_SCREEN.root.name
        ) {
            mainNavGraph(navController, sharedViewModel, homeViewModel, notificationViewModel, cardViewModel, mindHistoryViewModel)
            postMindNavGraph(navController, mindPostViewModel, sharedViewModel)
            memoEditNavGraph(navController, memoEditViewModel, sharedViewModel)
            inviteNavGraph(navController, inviteViewModel, sharedViewModel)
            notificationGraph(navController, notificationViewModel)
            mindCardGraph(navController, cardViewModel)
            friendGraph(navController, friendViewModel, sharedViewModel)
            userProfileGraph(navController, inviteViewModel, userProfileViewModel, friendViewModel)
            settingGraph(navController)
        }

        if(isNavigationBarVisible) {
            BottomNavigationBar(
                selectedDestination = navController.currentBackStackEntry?.destination?.route!!,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }

    }
}

