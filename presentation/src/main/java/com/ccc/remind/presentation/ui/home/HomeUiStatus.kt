package com.ccc.remind.presentation.ui.home

import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.notification.Notification

data class HomeUiStatus(
    val post: MindPost? = null,
    val friendPost: MindPost? = null,
    val notifications: List<Notification> = emptyList()
) {
    val isNotificationAlarmOn: Boolean
        get() {
            val result = notifications.any { !it.isRead }
            return result
        }
}