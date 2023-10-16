package com.ccc.remind.presentation.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ccc.remind.R

data class TopLevelDestination(
    val route: Route,
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val iconTextId: Int
)

class NavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route.name) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo(destination: Route) {
        navController.navigate(destination.name) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = Route.Main.Cards,
        selectedIconId = R.drawable.ic_stack_up_square,
        unselectedIconId = R.drawable.ic_stack_up_square,
        iconTextId = R.string.tab_cards
    ),
    TopLevelDestination(
        route = Route.Main.Home,
        selectedIconId = R.drawable.ic_home,
        unselectedIconId = R.drawable.ic_home,
        iconTextId = R.string.tab_home
    ),
    TopLevelDestination(
        route = Route.Main.User,
        selectedIconId = R.drawable.ic_user,
        unselectedIconId = R.drawable.ic_user,
        iconTextId = R.string.tab_user
    ),
)