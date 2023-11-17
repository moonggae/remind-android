package com.ccc.remind.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.card.CardBookmarkListScreen
import com.ccc.remind.presentation.ui.card.CardDetailScreen
import com.ccc.remind.presentation.ui.card.CardListDetailScreen
import com.ccc.remind.presentation.ui.card.CardListScreen
import com.ccc.remind.presentation.ui.card.CardViewModel
import com.ccc.remind.presentation.ui.friend.FriendListScreen
import com.ccc.remind.presentation.ui.friend.FriendViewModel
import com.ccc.remind.presentation.ui.friend.UserProfileFriendScreen
import com.ccc.remind.presentation.ui.friend.invite.InviteScreen
import com.ccc.remind.presentation.ui.friend.invite.InviteViewModel
import com.ccc.remind.presentation.ui.friend.invite.UserProfileInviteScreen
import com.ccc.remind.presentation.ui.history.MindHistoryScreen
import com.ccc.remind.presentation.ui.history.MindHistoryViewModel
import com.ccc.remind.presentation.ui.home.HomeScreen
import com.ccc.remind.presentation.ui.home.HomeViewModel
import com.ccc.remind.presentation.ui.memo.MemoEditScreen
import com.ccc.remind.presentation.ui.memo.MemoEditViewModel
import com.ccc.remind.presentation.ui.mindPost.MindPostDetailScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostEditScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostSelectCardScreen
import com.ccc.remind.presentation.ui.mindPost.MindPostViewModel
import com.ccc.remind.presentation.ui.mindPost.PostViewType
import com.ccc.remind.presentation.ui.notification.NotificationListScreen
import com.ccc.remind.presentation.ui.notification.NotificationViewModel
import com.ccc.remind.presentation.ui.user.UserProfileEditScreen
import com.ccc.remind.presentation.ui.user.UserProfileEditViewModel
import com.ccc.remind.presentation.ui.user.UserScreen
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileDefaultScreen
import com.ccc.remind.presentation.ui.user.userProfile.UserProfileViewModel
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

    object Main : Route("Main") {
        object Home : Route("Main.Home", Main)
        object MindHistory : Route("Main.MindHistory", Main)
        object Cards : Route("Main.Cards", Main)
        object User : Route("Main.User", Main) {
            object ProfileEdit : Route("Main.User.ProfileEdit", User)
        }
    }

    object MindPost : Route("MindPost") {
        object SelectCard : Route("MindPost.SelectCard", MindPost)
        object Edit : Route("MindPost.Edit", MindPost)
        object Detail : Route("MindPost.Detail", MindPost)
    }

    object MindCard : Route("MindCard") {
        object BookmarkList : Route("MindCard.BookmarkList", MindCard)
        object Detail : Route("MindCard.Detail", MindCard)
        object DetailCardList : Route("MindCard.DetailCardList", MindCard)
    }

    object MemoEdit : Route("MemoEdit")

    object Invite : Route("Invite")

    object Friend : Route("Friend")

    object UserProfile : Route("UserProfile") {
        object Default : Route("UserProfile.Default", UserProfile)
        object Friend : Route("UserProfile.Friend", UserProfile)
        object Invite : Route("UserProfile.Invite", UserProfile)
    }

    object NotificationList : Route("NotificationList")

    val root: Route
        get() = parent?.root ?: this
}

fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    homeViewModel: HomeViewModel,
    notificationViewModel: NotificationViewModel,
    cardViewModel: CardViewModel,
    mindHistoryViewModel: MindHistoryViewModel
) {
    val startDestination =
        if (Constants.START_TOP_SCREEN.root == Route.Main) Constants.START_TOP_SCREEN
        else Route.Main.Home

    navigation(
        startDestination = startDestination.name,
        route = Route.Main.name,
    ) {
        composable(Route.Main.Cards.name) {
            CardListScreen(
                navController = navController,
                viewModel = cardViewModel
            )
        }
        composable(Route.Main.Home.name) {
            LaunchedEffect(navController.currentDestination) {
                if (navController.currentDestination?.route?.startsWith(Route.Main.Home.name) == true) {
                    homeViewModel.initUiState()
                    notificationViewModel.initNotifications()
                }
            }

            HomeScreen(
                navController = navController,
                viewModel = homeViewModel,
                notificationViewModel = notificationViewModel,
                sharedViewModel = sharedViewModel
            )
        }
        composable(Route.Main.MindHistory.name) {
            MindHistoryScreen(
                navController = navController,
                viewModel = mindHistoryViewModel
            )
        }
        composable(Route.Main.User.name) {
            UserScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
        composable(Route.Main.User.ProfileEdit.name) {
            val userProfileEditViewModel: UserProfileEditViewModel = hiltViewModel()
            userProfileEditViewModel.initUserProfile()
            UserProfileEditScreen(
                navController = navController,
                viewModel = userProfileEditViewModel,
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
    val startDestination =
        if (Constants.START_TOP_SCREEN.root == Route.MindPost) Constants.START_TOP_SCREEN
        else Route.MindPost.SelectCard
    navigation(
        startDestination = startDestination.name,
        route = Route.MindPost.name
    ) {
        composable(
            route = "${Route.MindPost.SelectCard.name}?cardId={cardId}&type={type}",
            arguments = listOf(navArgument("cardId") {
                nullable = true
                type = NavType.StringType
                defaultValue = null
            }, navArgument("type") {
                nullable = true
                type = NavType.StringType
                defaultValue = PostViewType.FIRST_POST.name
            })
        ) {
            LaunchedEffect(navController.currentBackStackEntry) {
                if (navController.currentDestination?.route?.startsWith(Route.MindPost.SelectCard.name) == true) {
                    it.arguments?.getString("cardId")?.let { cardId ->
                        viewModel.setInitialCard(listOf(cardId.toInt()))
                    }
                    if (it.arguments?.getString("type") == PostViewType.FIRST_POST.name) {
                        viewModel.updateBackStackEntryId(navController.currentBackStackEntry?.id)
                    }
                }
            }
            MindPostSelectCardScreen(
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
        composable(
            route = "${Route.MindPost.Detail.name}?id={id}&type={type}",
            arguments = listOf(navArgument("id") {
                nullable = true
                type = NavType.StringType
                defaultValue = null
            }, navArgument("type") {
                nullable = true
                type = NavType.StringType
                defaultValue = PostViewType.FIRST_POST.name
            })
        ) {
            LaunchedEffect(navController.currentDestination) {
                if (navController.currentDestination?.route?.startsWith(Route.MindPost.Detail.name) == true) {
                    val postId = it.arguments?.getString("id")
                    postId?.toIntOrNull()?.let { id ->
                        viewModel.setOpenedMindPost(id)
                    }

                    it.arguments?.getString("type")?.let { typeString ->
                        try {
                            viewModel.setViewType(PostViewType.valueOf(typeString))
                        } catch (_: Exception) {
                        }
                    }
                }
            }

            MindPostDetailScreen(
                navController = navController,
                viewModel = viewModel,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

fun NavGraphBuilder.memoEditNavGraph(
    navController: NavController,
    viewModel: MemoEditViewModel,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = "${Route.MemoEdit.name}/{postId}/{memoId}/{isFriend}",
        arguments = listOf(
            navArgument("postId") { type = NavType.IntType },
            navArgument("memoId") { type = NavType.IntType },
            navArgument("isFriend") { type = NavType.BoolType }
        )
    ) {
        if (it.lifecycle.currentState == Lifecycle.State.STARTED) {
            viewModel.setInitData(
                postId = it.arguments!!.getInt("postId"),
                memoId = it.arguments?.getInt("memoId"),
                isFriend = it.arguments?.getBoolean("isFriend")
            )
            viewModel.fetchMemoData()
        }

        MemoEditScreen(
            navController = navController,
            viewModel = viewModel,
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
}

fun NavGraphBuilder.notificationGraph(
    navController: NavController,
    viewModel: NotificationViewModel
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.route == Route.NotificationList.name) {
            viewModel.updateNotificationsReadAll()
        }
    }

    composable(
        route = Route.NotificationList.name
    ) {
        NotificationListScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}

fun NavGraphBuilder.mindCardGraph(
    navController: NavController,
    viewModel: CardViewModel
) {
    composable(route = Route.MindCard.BookmarkList.name) {
        CardBookmarkListScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
    composable(
        route = "${Route.MindCard.Detail.name}/{mindCardId}",
        arguments = listOf(
            navArgument("mindCardId") { type = NavType.IntType },
        )
    ) {
        viewModel.setOpenedCard(it.arguments?.getInt("mindCardId") ?: -1)
        CardDetailScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
    composable(
        route = "${Route.MindCard.DetailCardList.name}?cardId={cardId}",
        arguments = listOf(navArgument("cardId") {
            nullable = true
            type = NavType.StringArrayType
            defaultValue = null
        })
    ) {
        var cardIds by remember {
            mutableStateOf(emptyList<Int>())
        }

        LaunchedEffect(navController.currentBackStackEntry) {
            if (navController.currentDestination?.route?.startsWith(Route.MindCard.DetailCardList.name) == true) {
                cardIds = (it.arguments?.getStringArray("cardId")?.toList() ?: emptyList()).map { id -> id.toInt() }
                if (cardIds.size > 1) {
                    viewModel.setDetailCardListIds(cardIds)
                } else if (cardIds.isNotEmpty()) {
                    viewModel.setOpenedCard(cardIds.first())
                }
            }
        }

        if (cardIds.size > 1) {
            CardListDetailScreen(
                navController = navController,
                viewModel = viewModel
            )
        } else {
            CardDetailScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

fun NavGraphBuilder.friendGraph(
    navController: NavController,
    viewModel: FriendViewModel,
    sharedViewModel: SharedViewModel
) {
    composable(Route.Friend.name) {
        FriendListScreen(
            navController = navController,
            viewModel = viewModel,
            sharedViewModel = sharedViewModel
        )
    }
}

fun NavGraphBuilder.userProfileGraph(
    navController: NavController,
    inviteViewModel: InviteViewModel,
    userProfileViewModel: UserProfileViewModel,
    friendViewModel: FriendViewModel
) {
    composable(Route.UserProfile.Invite.name)
    {
        UserProfileInviteScreen(
            navController = navController,
            viewModel = userProfileViewModel,
            inviteViewModel = inviteViewModel
        )
    }

    composable(
        route = "${Route.UserProfile.Friend.name}?id={id}",
        arguments = listOf(navArgument("id") {
            nullable = true
            type = NavType.StringType
            defaultValue = null
        })
    ) {
        UserProfileFriendScreen(
            navController = navController,
            viewModel = userProfileViewModel,
            friendViewModel = friendViewModel,
            userId = it.arguments?.getString("id")
        )
    }

    composable(
        route = "${Route.UserProfile.Default.name}?id={id}",
        arguments = listOf(navArgument("id") {
            nullable = true
            type = NavType.StringType
            defaultValue = null
        })
    ) {
        UserProfileDefaultScreen(
            navController = navController,
            viewModel = userProfileViewModel,
            userId = it.arguments?.getString("id")
        )
    }
}