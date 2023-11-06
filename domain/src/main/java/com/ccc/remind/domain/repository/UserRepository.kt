package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.entity.user.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getLoggedInUser() : Flow<User?>

    suspend fun replaceLoggedInUser(user: User)

    suspend fun deleteLoggedInUser()

    fun getUserProfile(): Flow<UserProfile>

    suspend fun updateUserDisplayName(displayName: String)

    suspend fun updateUserProfile(displayName: String?, profileImage: ImageFile?)

    suspend fun updateFCMToken(token: String)

    suspend fun updateLocalUser(
        accessToken: String? = null,
        refreshToken: String? = null,
        displayName: String? = null,
        logInType: LogInType? = null,
        profileImage: ImageFile? = null,
        inviteCode: String? = null
    ): User?
}