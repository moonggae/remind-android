package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.remote.model.user.LoginResponse
import com.ccc.remind.domain.entity.user.JwtToken

fun LoginResponse.toJwtToken() = JwtToken(
    accessToken, refreshToken
)