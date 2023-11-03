package com.ccc.remind.data.source.local

import com.ccc.remind.data.source.local.dao.NotificationDao
import com.ccc.remind.data.source.local.model.NotificationEntity
import com.ccc.remind.data.util.Constants.NOTIFICATION_LOAD_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NotificationLocalDataSource(
    private val notificationDao: NotificationDao, private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchNotifications(page: Int): List<NotificationEntity> = withContext(ioDispatcher) {
        notificationDao.get(page, NOTIFICATION_LOAD_SIZE)
    }

    suspend fun pushNotification(entity: NotificationEntity): NotificationEntity = withContext(ioDispatcher) {
        val rowId = notificationDao.insert(entity)
        notificationDao.getByRowId(rowId)
    }

    suspend fun updateNotificationsReadAll() = withContext(ioDispatcher) {
        notificationDao.updateReadAll()
    }
}