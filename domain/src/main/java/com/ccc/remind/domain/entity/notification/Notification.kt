package com.ccc.remind.domain.entity.notification

import java.time.ZonedDateTime

data class Notification(
    val createdAt: ZonedDateTime,
    val isRead: Boolean,
    val title: String?,
    val body: String?,
    val type: String?,
    val targetId: String?
)