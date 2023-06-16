package com.ccc.remind.data.mapper

import com.ccc.remind.data.model.LoginResponse
import com.ccc.remind.domain.entity.JwtToken

fun LoginResponse.toJwtToken() = JwtToken(
    accessToken, refreshToken
)