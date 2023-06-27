package com.ccc.remind.data.repositoryImp

import com.ccc.remind.data.mapper.toData
import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.mapper.toJwtToken
import com.ccc.remind.data.model.DisplayNameDto
import com.ccc.remind.data.model.LoggedInUserEntity
import com.ccc.remind.data.model.LoginRequest
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.remote.LoginRemoteService
import com.ccc.remind.domain.entity.JwtToken
import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val loginRemoteService: LoginRemoteService
) : UserRepository {
    override fun login(uid: String, logInType: LogInType): Flow<JwtToken> = flow {
        emit(loginRemoteService.login(LoginRequest(uid, logInType.name)).body()!!.toJwtToken())
    }

    override fun getLoggedInUser(): Flow<LoggedInUser?> = flow {
        emit(userLocalDataSource.fetchLoggedInUser()?.toDomain())
    }

    override suspend fun updateLoggedInUser(loggedInUser: LoggedInUser) =
        userLocalDataSource.postLoggedInUser(loggedInUser.toData())

    override fun getUserDisplayName(): Flow<String?> = flow {
        emit(loginRemoteService.fetchDisplayName().body()?.displayName)
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