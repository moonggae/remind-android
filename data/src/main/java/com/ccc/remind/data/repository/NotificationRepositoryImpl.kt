package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.mapper.toEntity
import com.ccc.remind.data.source.local.NotificationLocalDataSource
import com.ccc.remind.domain.entity.notification.Notification
import com.ccc.remind.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationRepositoryImpl(
    private val notificationLocalDataSource: NotificationLocalDataSource
): NotificationRepository {
    override fun getNotifications(page: Int): Flow<List<Notification>> = flow {
        emit(notificationLocalDataSource.fetchNotifications(page).map { it.toDomain() })
    }

    override suspend fun postNotification(entity: Notification) {
        notificationLocalDataSource.pushNotification(entity.toEntity())
    }

    override suspend fun updateNotificationReadAll() {
        notificationLocalDataSource.updateNotificationsReadAll()
    }
}