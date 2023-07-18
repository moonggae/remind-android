package com.ccc.remind.presentation.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ccc.remind.R

object Route {
    const val CARDS = "Cards"
    const val HOME = "Home"
    const val USER = "User"
}

data class TopLevelDestination(
    val route: String,
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val iconTextId: Int
)

class NavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
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
        route = Route.CARDS,
        selectedIconId = R.drawable.ic_stack_up_square,
        unselectedIconId = R.drawable.ic_stack_up_square,
        iconTextId = R.string.tab_cards
    ),
    TopLevelDestination(
        route = Route.HOME,
        selectedIconId = R.drawable.ic_home,
        unselectedIconId = R.drawable.ic_home,
        iconTextId = R.string.tab_home
    ),
    TopLevelDestination(
        route = Route.USER,
        selectedIconId = R.drawable.ic_user,
        unselectedIconId = R.drawable.ic_user,
        iconTextId = R.string.tab_user
    ),
)