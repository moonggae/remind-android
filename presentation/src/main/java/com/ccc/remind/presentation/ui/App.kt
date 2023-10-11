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
import com.ccc.remind.presentation.ui.invite.InviteViewModel
import com.ccc.remind.presentation.ui.memo.MemoEditViewModel
import com.ccc.remind.presentation.ui.mindPost.MindPostViewModel
import com.ccc.remind.presentation.ui.navigation.BottomNavigationBar
import com.ccc.remind.presentation.ui.navigation.NavigationActions
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.navigation.inviteNavGraph
import com.ccc.remind.presentation.ui.navigation.mainNavGraph
import com.ccc.remind.presentation.ui.navigation.memoEditNavGraph
import com.ccc.remind.presentation.ui.navigation.postMindNavGraph
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
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
        val inviteViewModel: InviteViewModel = hiltViewModel()
        val memoEditViewModel: MemoEditViewModel = hiltViewModel()

        NavHost(
            navController = navController as NavHostController,
            modifier = Modifier.weight(1f),
            startDestination = Constants.START_TOP_SCREEN.root.name
        ) {
            mainNavGraph(navController, sharedViewModel)
            postMindNavGraph(navController, mindPostViewModel, sharedViewModel)
            memoEditNavGraph(navController, memoEditViewModel, sharedViewModel)
            inviteNavGraph(navController, inviteViewModel, sharedViewModel)
        }

        if(isNavigationBarVisible) {
            BottomNavigationBar(
                selectedDestination = navController.currentBackStackEntry?.destination?.route!!,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }

    }
}