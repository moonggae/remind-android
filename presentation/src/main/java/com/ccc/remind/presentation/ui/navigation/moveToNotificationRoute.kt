package com.ccc.remind.presentation.ui.navigation

import androidx.navigation.NavController
import com.ccc.remind.presentation.util.notification.NotificationType

fun moveToNotificationRoute(navController: NavController, notificationType: String?, notificationTargetId: String?) {
    if(notificationType == null) return

    when(NotificationType(notificationType)) {
        NotificationType.FRIEND_ACCEPT,
        NotificationType.MIND_REQUEST,
        NotificationType.MIND_POST -> navController.navigate(Route.Main.Home.name)

        NotificationType.MEMO_UPDATE,
        NotificationType.MEMO_COMMENT,
        NotificationType.MEMO_POST -> navController.navigate("${Route.MemoEdit.name}/-1/${notificationTargetId ?: "-1"}/true")

        NotificationType.FRIEND_REQUEST -> navController.navigate(Route.Invite.name)
        else -> {}
    }
}