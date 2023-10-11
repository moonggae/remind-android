package com.ccc.remind.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "notification")
data class NotificationEntity(
    @PrimaryKey(true)
    val id: Int? = null,
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    val isRead: Boolean,
    val title: String?,
    val body: String?,
    val type: String?,
    val targetId: String?
)