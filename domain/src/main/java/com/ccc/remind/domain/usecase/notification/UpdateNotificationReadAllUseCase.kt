package com.ccc.remind.domain.usecase.notification

import com.ccc.remind.domain.repository.NotificationRepository
import javax.inject.Inject

class UpdateNotificationReadAllUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke() = notificationRepository.updateNotificationReadAll()
}