package com.ccc.remind.data.repositoryImp

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.mapper.toJwtToken
import com.ccc.remind.data.model.LoginRequest
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.remote.LoginService
import com.ccc.remind.domain.entity.JwtToken
import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(private val userLocalDataSource: UserLocalDataSource, private val loginService: LoginService) : UserRepository {
    override suspend fun login(uid: String, logInType: LogInType): JwtToken = loginService.login(LoginRequest(uid, logInType.name)).body()!!.toJwtToken()

    override fun getLoggedInUser(): Flow<LoggedInUser?> = userLocalDataSource.getLoggedInUser().map { it?.toDomain() }

    override suspend fun getUserDisplayName(): String? = loginService.getDisplayName().body()?.displayName

    override suspend fun updateUserDisplayName(displayName: String) {
        TODO("Not yet implemented")
    }
}