package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.local.model.NotificationEntity
import com.ccc.remind.domain.entity.notification.Notification

fun NotificationEntity.toDomain(): Notification = Notification(
    createdAt, isRead, title, body, type, targetId
)

fun Notification.toEntity(id: Int? = null): NotificationEntity = NotificationEntity(
    id, createdAt, isRead, title, body, type, targetId
)