package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.local.model.LoggedInUserEntity
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.LoggedInUser

// domain -> data
fun LoggedInUser.toData() = LoggedInUserEntity(
    accessToken,
    refreshToken,
    displayName,
    logInType.name
)

fun LoggedInUserEntity.toDomain() = LoggedInUser(
    accessToken,
    refreshToken,
    displayName,
    LogInType.valueOf(logInType)
)