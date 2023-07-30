package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toData
import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.mapper.toJwtToken
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.local.model.UserEntity
import com.ccc.remind.data.source.remote.LoginRemoteService
import com.ccc.remind.data.source.remote.model.user.DisplayNameDto
import com.ccc.remind.data.source.remote.model.user.LoginRequest
import com.ccc.remind.domain.entity.user.JwtToken
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val loginRemoteService: LoginRemoteService
) : UserRepository {
    override fun login(accessToken: String, logInType: LogInType): Flow<JwtToken> = flow {
        when(logInType) {
            LogInType.KAKAO ->
                emit(loginRemoteService.loginKakao(LoginRequest(accessToken)).body()!!.toJwtToken())
            else -> {
                // todo: 다른 로그인 구현시 추가
            }
        }
    }

    override fun getLoggedInUser(): Flow<User?> = flow {
        emit(userLocalDataSource.fetchLoggedInUser()?.toDomain())
    }

    override suspend fun replaceLoggedInUser(user: User) {
        userLocalDataSource.deleteLoggedInUser()
        userLocalDataSource.postLoggedInUser(user.toData())
    }

    override fun getUserDisplayName(): Flow<String?> = flow {
        var remoteDisplayName: String? = loginRemoteService.fetchDisplayName().body()?.displayName
        emit(remoteDisplayName)
        if(remoteDisplayName != null) updateLocalUserDisplayName(remoteDisplayName)
    }

    override suspend fun updateUserDisplayName(displayName: String) {
        loginRemoteService.updateDisplayName(DisplayNameDto(displayName))
        updateLocalUserDisplayName(displayName)
    }

    private suspend fun updateLocalUserDisplayName(displayName: String) {
        val entity = userLocalDataSource.fetchLoggedInUser()
        if (entity != null && entity.displayName != displayName)
            userLocalDataSource.updateLoggedInUser(
                UserEntity(
                    entity.accessToken,
                    entity.refreshToken,
                    displayName,
                    entity.logInType
                )
            )
    }
}