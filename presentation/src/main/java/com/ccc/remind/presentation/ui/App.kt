package com.ccc.remind.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.presentation.ui.home.HomeScreen
import com.ccc.remind.presentation.ui.main.MainUIState
import com.ccc.remind.presentation.ui.navigation.BottomNavigationBar
import com.ccc.remind.presentation.ui.navigation.NavigationActions
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.navigation.TopLevelDestination


@Composable
fun App(
    MainUIState: MainUIState
) {
    NavigationWrapper(
        HomeUIState = MainUIState
    )
}

@Composable
private fun NavigationWrapper(
    HomeUIState: MainUIState
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: Route.HOME

    AppContent(
        MainUIState = HomeUIState,
        navController = navController,
        selectedDestination = selectedDestination,
        navigateToTopLevelDestination = navigationActions::navigateTo
    )
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    MainUIState: MainUIState,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit
) {
    Row(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            NavHost(
                navController = navController,
                MainUIState = MainUIState,
                modifier = Modifier.weight(1f),
            )
            BottomNavigationBar(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigateToTopLevelDestination
            )
        }
    }
}

@Composable
private fun NavHost(
    navController: NavHostController,
    MainUIState: MainUIState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.HOME,
    ) {
        composable(Route.CARDS) {
            EmptyComingSoon()
        }
        composable(Route.HOME) {
            HomeScreen()
        }
        composable(Route.USER) {
            EmptyComingSoon()
        }
    }
}
