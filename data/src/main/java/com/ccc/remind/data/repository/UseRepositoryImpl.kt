package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toData
import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.local.model.UserEntity
import com.ccc.remind.data.source.remote.UserRemoteService
import com.ccc.remind.data.source.remote.model.user.DisplayNameDto
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteService: UserRemoteService
) : UserRepository {
    override fun getLoggedInUser(): Flow<User?> = flow {
        emit(userLocalDataSource.fetchLoggedInUser()?.toDomain())
    }

    override suspend fun replaceLoggedInUser(user: User) {
        userLocalDataSource.deleteLoggedInUser()
        userLocalDataSource.postLoggedInUser(user.toData())
    }

    override fun getUserDisplayName(): Flow<String?> = flow {
        var remoteDisplayName: String? = userRemoteService.fetchDisplayName().body()?.displayName
        emit(remoteDisplayName)
        if(remoteDisplayName != null) updateLocalUser(displayName = remoteDisplayName)
    }

    override suspend fun updateUserDisplayName(displayName: String) {
        userRemoteService.updateDisplayName(DisplayNameDto(displayName))
        updateLocalUser(displayName = displayName)
    }

    override suspend fun updateLocalUser(accessToken: String?, refreshToken: String?, displayName: String?, logInType: LogInType?) {
        val entity = userLocalDataSource.fetchLoggedInUser()
        if(entity != null) {
            userLocalDataSource.deleteLoggedInUser()
            userLocalDataSource.postLoggedInUser(
                UserEntity(
                    accessToken = accessToken ?: entity.accessToken,
                    refreshToken = refreshToken ?: entity.refreshToken,
                    displayName = displayName ?: entity.displayName,
                    logInType = (logInType ?: entity.logInType) as String
            ))
        }
    }
}