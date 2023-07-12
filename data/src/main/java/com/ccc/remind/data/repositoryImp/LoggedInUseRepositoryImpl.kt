package com.ccc.remind.data.repositoryImp

import com.ccc.remind.data.mapper.toData
import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.mapper.toJwtToken
import com.ccc.remind.data.source.remote.model.DisplayNameDto
import com.ccc.remind.data.source.local.model.LoggedInUserEntity
import com.ccc.remind.data.source.remote.model.LoginRequest
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.remote.LoginRemoteService
import com.ccc.remind.domain.entity.JwtToken
import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser
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

    override fun getLoggedInUser(): Flow<LoggedInUser?> = flow {
        emit(userLocalDataSource.fetchLoggedInUser()?.toDomain())
    }

    override suspend fun replaceLoggedInUser(loggedInUser: LoggedInUser) {
        userLocalDataSource.deleteLoggedInUser()
        userLocalDataSource.postLoggedInUser(loggedInUser.toData())
    }

    override fun getUserDisplayName(): Flow<String?> = flow {
        var remoteDisplayName: String? = loginRemoteService.fetchDisplayName().body()?.displayName
        emit(remoteDisplayName)
        if(remoteDisplayName != null) updateUserDisplayName(remoteDisplayName)
    }

    override suspend fun updateUserDisplayName(displayName: String) {
        loginRemoteService.updateDisplayName(DisplayNameDto(displayName))
        val entity = userLocalDataSource.fetchLoggedInUser()
        if (entity != null)
            userLocalDataSource.updateLoggedInUser(
                LoggedInUserEntity(
                    entity.accessToken,
                    entity.refreshToken,
                    displayName,
                    entity.logInType
                )
            )
    }
}