package com.ccc.remind.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "setting")
data class SettingEntity(
    @PrimaryKey
    val key: String,
    val value: String
)