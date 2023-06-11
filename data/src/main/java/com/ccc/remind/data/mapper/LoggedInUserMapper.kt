package com.ccc.remind.data.mapper

import com.ccc.remind.data.model.LoggedInUserEntity
import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser

// domain -> data
fun LoggedInUser.toDataModel() = LoggedInUserEntity(
    accessToken,
    refreshToken,
    displayName,
    logInType.name
)

fun LoggedInUserEntity.toDomainModel() = LoggedInUser(
    accessToken,
    refreshToken,
    displayName,
    LogInType.valueOf(logInType)
)