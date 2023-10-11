package com.ccc.remind.domain.usecase.notification

import com.ccc.remind.domain.entity.notification.Notification
import com.ccc.remind.domain.repository.NotificationRepository
import java.time.ZonedDateTime
import javax.inject.Inject

class PostNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(title: String?, body: String?, type: String?, targetId: String?) =
        notificationRepository.postNotification(
            Notification(
                createdAt = ZonedDateTime.now(),
                isRead = false,
                title,
                body,
                type,
                targetId
            )
        )
}