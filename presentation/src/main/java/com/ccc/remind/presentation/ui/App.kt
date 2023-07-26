package com.ccc.remind.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.presentation.ui.main.MainUIState
import com.ccc.remind.presentation.ui.navigation.BottomNavigationBar
import com.ccc.remind.presentation.ui.navigation.NavigationActions
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.navigation.mainNavGraph
import com.ccc.remind.presentation.ui.navigation.postMindNavGraph


@Composable
fun App(
    MainUIState: MainUIState,
    viewModel: SharedViewModel = hiltViewModel()
) {
    NavigationWrapper(
        HomeUIState = MainUIState,
        viewModel
    )
}

@Composable
private fun NavigationWrapper(
    HomeUIState: MainUIState,
    viewModel: SharedViewModel
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination?.route

    val isNavigationBarVisible = destination?.startsWith(Route.Main.name) ?: false

    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            modifier = Modifier.weight(1f),
            startDestination = Route.MindPost.CardList.name
        ) {
            mainNavGraph(navController)
            postMindNavGraph(navController)
        }

        if(isNavigationBarVisible) {
            BottomNavigationBar(
                selectedDestination = navController.currentBackStackEntry?.destination?.route!!,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }

    }
}