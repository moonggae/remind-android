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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ccc.remind.presentation.ui.memo.MemoEditScreen
import com.ccc.remind.presentation.ui.memo.MemoEditViewModel
import com.ccc.remind.presentation.ui.mindPost.MindPostViewModel
import com.ccc.remind.presentation.ui.navigation.BottomNavigationBar
import com.ccc.remind.presentation.ui.navigation.NavigationActions
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.navigation.mainNavGraph
import com.ccc.remind.presentation.ui.navigation.postMindNavGraph
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.Constants


@Preview
@Composable
fun AppPreview() {
    App()
}

@Composable
fun App(
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    NavigationWrapper()
}

@Composable
private fun NavigationWrapper() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
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
        NavHost(
            navController = navController,
            modifier = Modifier.weight(1f),
            startDestination = Constants.START_TOP_SCREEN.root.name
        ) {
            mainNavGraph(navController)
            postMindNavGraph(navController, mindPostViewModel)
            composable(
                route = "${Route.MemoEdit.name}/{postId}/{memoId}",
                arguments = listOf(
                    navArgument("postId") { type = NavType.IntType },
                    navArgument("memoId") { type = NavType.IntType }
                )
            ) {
                val memoEditViewModel: MemoEditViewModel = hiltViewModel()
                memoEditViewModel.setInitData(
                    postId = it.arguments!!.getInt("postId"),
                    memoId = it.arguments?.getInt("memoId")
                )
                MemoEditScreen(
                    navController = navController,
                    memoEditViewModel
                )
            }
        }

        if(isNavigationBarVisible) {
            BottomNavigationBar(
                selectedDestination = navController.currentBackStackEntry?.destination?.route!!,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }

    }
}