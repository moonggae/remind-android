package com.ccc.remind.presentation.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ccc.remind.presentation.ui.EmptyComingSoon
import com.ccc.remind.presentation.ui.home.HomeScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostCardListScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostCompleteScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostEditScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostViewModel
import com.ccc.remind.presentation.util.Constants

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

    val root: Route
        get() = parent?.root ?: this
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    val startDestination =
        if(Constants.START_TOP_SCREEN.root == Route.Main) Constants.START_TOP_SCREEN
        else Route.Main.Home
    navigation(
        startDestination = startDestination.name,
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


fun NavGraphBuilder.postMindNavGraph(
    navController: NavController,
    viewModel: MindPostViewModel
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if(destination.route == Route.Main.Home.name) {
            viewModel.initUiState()
        }
    }

    val startDestination =
        if(Constants.START_TOP_SCREEN.root == Route.MindPost) Constants.START_TOP_SCREEN
        else Route.MindPost.CardList
    navigation(
        startDestination = startDestination.name,
        route = Route.MindPost.name
    ) {
        composable(Route.MindPost.CardList.name) {
            MindPostCardListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(Route.MindPost.Edit.name) {
            MindPostEditScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(Route.MindPost.Complete.name) {
            MindPostCompleteScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}