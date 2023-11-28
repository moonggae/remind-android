package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.user.CurrentUser
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import kotlinx.coroutines.flow.Flow

interface CurrentUserRepository {

    suspend fun getLoggedInUser() : Flow<CurrentUser?>

    suspend fun replaceLoggedInUser(currentUser: CurrentUser)

    suspend fun deleteLoggedInUser()

    fun getUserProfile(): Flow<User?>

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
    ): CurrentUser?
}