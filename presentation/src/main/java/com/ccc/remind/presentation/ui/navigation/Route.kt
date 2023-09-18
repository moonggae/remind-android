package com.ccc.remind.presentation.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.ccc.remind.presentation.ui.EmptyComingSoon
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.home.HomeScreen
import com.ccc.remind.presentation.ui.invite.InviteProfileScreen
import com.ccc.remind.presentation.ui.invite.InviteScreen
import com.ccc.remind.presentation.ui.invite.InviteViewModel
import com.ccc.remind.presentation.ui.memo.MemoEditScreen
import com.ccc.remind.presentation.ui.memo.MemoEditViewModel
import com.ccc.remind.presentation.ui.mindPost.MindPostCardListScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostCompleteScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostEditScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostViewModel
import com.ccc.remind.presentation.ui.user.UserProfileEditScreen
import com.ccc.remind.presentation.ui.user.UserProfileViewModel
import com.ccc.remind.presentation.ui.user.UserScreen
import com.ccc.remind.presentation.util.Constants

sealed class Route(val name: String, val parent: Route? = null) {

    companion object {
        private val allRoutes: List<Route> by lazy {
            Route::class.nestedClasses
                .mapNotNull { it.objectInstance as? Route }
                .flatMap { route ->
                    listOf(route) + collectSubRoutes(route)
                }
        }

        private fun collectSubRoutes(route: Route): List<Route> {
            return route::class.nestedClasses
                .mapNotNull { it.objectInstance as? Route }
                .flatMap { listOf(it) + collectSubRoutes(it) }
        }

        /* TODO
        * 리플렉션 사용으로 런타임 오류가 발생할 가능성이 있음
        * proguard 적용 후 정상작동 확인 필요
        * */
        fun fromName(name: String): Route? {
            return allRoutes.find { it.name == name }
        }
    }

    object Main: Route("Main") {
        object Home: Route("Main.Home", Main)
        object Cards: Route("Main.Cards", Main)
        object User: Route("Main.User", Main) {
            object ProfileEdit: Route("Main.User.ProfileEdit", User)
        }
    }
    object MindPost: Route("MindPost") {
        object CardList: Route("MindPost.CardList", MindPost)
        object Edit: Route("MindPost.Edit", MindPost)
        object Complete: Route("MindPost.Complete", MindPost)
    }

    object MemoEdit: Route("MemoEdit")

    object Invite: Route("Invite") {
        object Profile: Route("Invite.Profile", Invite)
    }

    val root: Route
        get() = parent?.root ?: this
}

fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
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
            HomeScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
        composable(Route.Main.User.name) {
            UserScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
        composable(Route.Main.User.ProfileEdit.name) {
            val userProfileViewModel: UserProfileViewModel = hiltViewModel()
            userProfileViewModel.initUserProfile()
            UserProfileEditScreen(
                navController =  navController,
                viewModel =  userProfileViewModel,
                sharedViewModel = sharedViewModel
            )
        }
    }
}


fun NavGraphBuilder.postMindNavGraph(
    navController: NavController,
    viewModel: MindPostViewModel,
    sharedViewModel: SharedViewModel
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
                viewModel = viewModel,
                sharedViewModel = sharedViewModel
            )
        }
        composable(Route.MindPost.Complete.name) {
            MindPostCompleteScreen(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

fun NavGraphBuilder.memoEditNavGraph(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
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
            memoEditViewModel,
            sharedViewModel = sharedViewModel
        )
    }
}

fun NavGraphBuilder.inviteNavGraph(
    navController: NavController,
    viewModel: InviteViewModel,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = Route.Invite.name
    ) {
        InviteScreen(
            navController = navController,
            viewModel = viewModel,
            sharedViewModel = sharedViewModel
        )
    }
    composable(
        route = Route.Invite.Profile.name
    ) {
        InviteProfileScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}