package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.notification.Notification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface NotificationRepository {
    val newNotificationFlow: SharedFlow<Notification>

    fun getNotifications(page: Int): Flow<List<Notification>>

    suspend fun postNotification(entity: Notification)

    suspend fun updateNotificationReadAll()
}