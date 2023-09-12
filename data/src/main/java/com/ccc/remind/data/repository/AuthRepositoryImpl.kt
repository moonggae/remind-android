package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toJwtToken
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.local.model.UserEntity
import com.ccc.remind.data.source.remote.AuthRemoteService
import com.ccc.remind.data.source.remote.model.user.LoginRequest
import com.ccc.remind.domain.entity.user.JwtToken
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authRemoteService: AuthRemoteService,
    private val userLocalDataSource: UserLocalDataSource
) : AuthRepository {
    override fun login(accessToken: String, logInType: LogInType): Flow<JwtToken> = flow {
        when(logInType) {
            LogInType.KAKAO -> {
                val response = authRemoteService.loginKakao(LoginRequest(accessToken)).body()!!.toJwtToken()
                userLocalDataSource.updateLoggedInUser(
                    UserEntity(
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        logInType = logInType.name,
                        displayName = null,
                        profileImage = null
                    )
                )
                emit(response)
            }
            else -> {
                // todo: 다른 로그인 구현시 추가
            }
        }
    }
    override suspend fun getNewToken(refreshToken: String): JwtToken? {
        val token = authRemoteService.getNewToken("Bearer $refreshToken").body()
        return if(token != null) {
            val user = userLocalDataSource.fetchLoggedInUser()
            if(user != null) {
                userLocalDataSource.updateLoggedInUser(user.copy(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken
                ))
            }
            token.toJwtToken()
        } else null
    }

    override suspend fun getToken(): JwtToken? {
        val loggedInUser = userLocalDataSource.fetchLoggedInUser()

        return if(loggedInUser != null)
            JwtToken(loggedInUser.accessToken, loggedInUser.refreshToken)
        else
            null
    }
}