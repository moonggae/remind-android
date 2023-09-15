package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.local.model.UserEntity
import com.ccc.remind.data.source.remote.model.user.UserProfileResponseDto
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.entity.user.UserProfile

// domain -> data
fun User.toData() = UserEntity(
    accessToken,
    refreshToken,
    displayName,
    logInType.name,
    profileImage?.toEntity(),
    inviteCode
)

fun UserEntity.toDomain() = User(
    accessToken,
    refreshToken,
    displayName,
    LogInType.valueOf(logInType),
    profileImage?.toDomain(),
    inviteCode
)

fun UserProfileResponseDto.toDomain() = UserProfile(
    displayName = displayName,
    profileImage = profileImage?.toDomain(),
    inviteCode
)

fun UserProfile.toData() = UserProfileResponseDto(
    displayName = displayName,
    profileImage = profileImage?.toData(),
    inviteCode
)