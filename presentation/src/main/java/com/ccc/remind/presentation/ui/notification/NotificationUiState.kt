package com.ccc.remind.presentation.ui.notification

import com.ccc.remind.domain.entity.notification.Notification

data class NotificationUiState(
    val notifications: List<Notification> = emptyList()
) {
    val isNotificationAlarmOn: Boolean
        get() {
            return notifications.any { !it.isRead }
        }
}
