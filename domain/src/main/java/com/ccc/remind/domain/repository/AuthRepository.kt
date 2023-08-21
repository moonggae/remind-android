package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.user.JwtToken
import com.ccc.remind.domain.entity.user.LogInType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(accessToken: String, logInType: LogInType) : Flow<JwtToken>

    suspend fun getNewToken(refreshToken: String): JwtToken?

    suspend fun getToken(): JwtToken?
}