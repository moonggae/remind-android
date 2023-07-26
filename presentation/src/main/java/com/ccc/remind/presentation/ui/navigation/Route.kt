package com.ccc.remind.presentation.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ccc.remind.presentation.ui.EmptyComingSoon
import com.ccc.remind.presentation.ui.home.HomeScreen

sealed class Route(val name: String, val parent: Route? = null) {
    object Main: Route("Main") {
        object Home: Route("Main.Home", Main)
        object Cards: Route("Main.Cards", Main)
        object User: Route("Main.User", Main)
    }
    object MindPost: Route("MindPost") {
        object CardList: Route("MindPost.CardList", MindPost)
        object Edit: Route("MindPost.Edit", MindPost)
        object Complete: Route("MindPost.Complete", MindPost)
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(
        startDestination = Route.Main.Home.name,
        route = Route.Main.name,
    ) {
        composable(Route.Main.Cards.name) {
            EmptyComingSoon(name = Route.Main.Cards.name)
        }
        composable(Route.Main.Home.name) {
            HomeScreen(navController)
        }
        composable(Route.Main.User.name) {
            EmptyComingSoon(name = Route.Main.User.name)
        }
    }
}


fun NavGraphBuilder.postMindNavGraph(navController: NavController) {
    navigation(
        startDestination = Route.MindPost.CardList.name,
        route = Route.MindPost.name
    ) {
        composable(Route.MindPost.CardList.name) {
            EmptyComingSoon(name = Route.MindPost.CardList.name)
        }
        composable(Route.MindPost.Edit.name) {
            EmptyComingSoon(name = Route.MindPost.Edit.name)
        }
        composable(Route.MindPost.Complete.name) {
            EmptyComingSoon(name = Route.MindPost.Complete.name)
        }
    }
}