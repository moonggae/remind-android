package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.local.model.UserEntity
import com.ccc.remind.domain.entity.user.CurrentUser
import com.ccc.remind.domain.entity.user.LogInType

// domain -> data
fun CurrentUser.toData() = UserEntity(
    accessToken,
    refreshToken,
    displayName,
    logInType.name,
    profileImage?.toEntity(),
    inviteCode
)

fun UserEntity.toDomain() = CurrentUser(
    accessToken,
    refreshToken,
    displayName,
    LogInType.valueOf(logInType),
    profileImage?.toDomain(),
    inviteCode
)