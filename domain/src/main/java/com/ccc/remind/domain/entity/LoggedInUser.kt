package com.ccc.remind.domain.entity

data class LoggedInUser(
    val accessToken: String,
    val refreshToken: String,
    val displayName: String,
    val logInType: LogInType
)