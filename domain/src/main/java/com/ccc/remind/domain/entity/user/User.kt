package com.ccc.remind.domain.entity.user

data class User(
    val accessToken: String,
    val refreshToken: String,
    val displayName: String?,
    val logInType: LogInType
)