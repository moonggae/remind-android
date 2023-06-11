package com.ccc.remind.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ccc.remind.domain.entity.LogInType

@Entity(tableName = "logged_in_user")
data class LoggedInUserEntity(
    @PrimaryKey
    val accessToken: String,
    val refreshToken: String,
    val displayName: String,
    val logInType: String
)