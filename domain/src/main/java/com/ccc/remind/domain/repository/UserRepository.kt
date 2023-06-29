package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.JwtToken
import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(uid: String, logInType: LogInType) : Flow<JwtToken>

    fun getLoggedInUser() : Flow<LoggedInUser?>

    suspend fun replaceLoggedInUser(loggedInUser: LoggedInUser)

    fun getUserDisplayName(): Flow<String?>

    suspend fun updateUserDisplayName(displayName: String)
}