package com.ccc.remind.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logged_in_user")
data class LoggedInUserEntity(
    @PrimaryKey
    val accessToken: String,
    val refreshToken: String,
    val displayName: String?,
    val logInType: String
)