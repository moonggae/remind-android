package com.ccc.remind.domain.entity

data class JwtToken(
    val accessToken: String,
    val refreshToken: String
)
