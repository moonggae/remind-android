package com.ccc.remind.domain.usecase.notification

import com.ccc.remind.domain.entity.notification.Notification
import com.ccc.remind.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(page: Int): Flow<List<Notification>> = notificationRepository.getNotifications(page)
}