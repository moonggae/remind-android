package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.JwtToken
import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(uid: String, logInType: LogInType) : JwtToken

    suspend fun getLoggedInUser() : LoggedInUser?

    suspend fun updateLoggedInUser(loggedInUser: LoggedInUser)

    suspend fun getUserDisplayName(): String?

    suspend fun updateUserDisplayName(displayName: String)
}