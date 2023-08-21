package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getLoggedInUser() : Flow<User?>

    suspend fun replaceLoggedInUser(user: User)

    fun getUserDisplayName(): Flow<String?>

    suspend fun updateUserDisplayName(displayName: String)

    suspend fun updateLocalUser(
        accessToken: String? = null,
        refreshToken: String? = null,
        displayName: String? = null,
        logInType: LogInType? = null
    )
}