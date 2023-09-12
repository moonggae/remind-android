package com.ccc.remind.data.source.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val accessToken: String,
    val refreshToken: String,
    val displayName: String?,
    val logInType: String,
    @Embedded val profileImage: ImageEntity?
)