package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.local.model.UserEntity
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User

// domain -> data
fun User.toData() = UserEntity(
    accessToken,
    refreshToken,
    displayName,
    logInType.name
)

fun UserEntity.toDomain() = User(
    accessToken,
    refreshToken,
    displayName,
    LogInType.valueOf(logInType)
)