package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.remote.model.user.TokenResponse
import com.ccc.remind.domain.entity.user.JwtToken

fun TokenResponse.toJwtToken() = JwtToken(
    accessToken, refreshToken
)