package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.mapper.toEntity
import com.ccc.remind.data.source.local.NotificationLocalDataSource
import com.ccc.remind.domain.entity.notification.Notification
import com.ccc.remind.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow

class NotificationRepositoryImpl(
    private val notificationLocalDataSource: NotificationLocalDataSource
): NotificationRepository {
    private val _notificationsFlow = MutableSharedFlow<Notification>()
    override val newNotificationFlow: SharedFlow<Notification> get() = _notificationsFlow


    override fun getNotifications(page: Int): Flow<List<Notification>> = flow {
        emit(notificationLocalDataSource.fetchNotifications(page).map { it.toDomain() })
    }

    override suspend fun postNotification(entity: Notification) {
        val postedEntity = notificationLocalDataSource.pushNotification(entity.toEntity())
        _notificationsFlow.emit(postedEntity.toDomain())
    }

    override suspend fun updateNotificationReadAll() {
        notificationLocalDataSource.updateNotificationsReadAll()
    }
}