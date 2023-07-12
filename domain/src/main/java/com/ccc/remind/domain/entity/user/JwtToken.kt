package com.ccc.remind.domain.entity.user

data class JwtToken(
    val accessToken: String,
    val refreshToken: String
)
