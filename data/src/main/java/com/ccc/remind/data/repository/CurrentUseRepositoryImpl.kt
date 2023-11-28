package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toData
import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.mapper.toEntity
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.remote.UserRemoteService
import com.ccc.remind.data.source.remote.model.user.DisplayNameDto
import com.ccc.remind.data.source.remote.model.user.UpdateFCMTokenRequestDto
import com.ccc.remind.data.source.remote.model.user.UserProfileUpdateDto
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.user.CurrentUser
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.CurrentUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrentUserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteService: UserRemoteService
) : CurrentUserRepository {
    companion object {
        private const val TAG = "UseRepositoryImpl"
    }
    
    override suspend fun getLoggedInUser(): Flow<CurrentUser?> = flow {
        val user = userLocalDataSource.fetchLoggedInUser()?.toDomain()
        emit(user)
    }

    override suspend fun replaceLoggedInUser(currentUser: CurrentUser) {
        userLocalDataSource.deleteLoggedInUser()
        userLocalDataSource.postLoggedInUser(currentUser.toData())
    }

    override suspend fun deleteLoggedInUser() {
        userLocalDataSource.deleteLoggedInUser()
    }

    override fun getUserProfile(): Flow<User?> = flow {
        val profile: User? = userRemoteService.fetchUserProfile().body()?.toDomain()
        emit(profile)
    }

    override suspend fun updateUserDisplayName(displayName: String) {
        userRemoteService.updateDisplayName(DisplayNameDto(displayName))
        updateLocalUser(displayName = displayName)
    }

    override suspend fun updateUserProfile(displayName: String?, profileImage: ImageFile?) {
        userRemoteService.updateProfile(UserProfileUpdateDto(
            displayName = displayName,
            profileImageId = profileImage?.id?.toString()
        ))
        updateLocalUser(
            displayName = displayName,
            profileImage = profileImage
        )
    }

    override suspend fun updateFCMToken(token: String) {
        userRemoteService.updateFCMToken(UpdateFCMTokenRequestDto(token))
    }

    override suspend fun updateLocalUser(
        accessToken: String?,
        refreshToken: String?,
        displayName: String?,
        logInType: LogInType?,
        profileImage: ImageFile?,
        inviteCode: String?
    ): CurrentUser? {
        userLocalDataSource.fetchLoggedInUser()?.let { entity ->
            return userLocalDataSource.updateLoggedInUser(entity.copy(
                accessToken = accessToken ?: entity.accessToken,
                refreshToken = refreshToken ?: entity.refreshToken,
                displayName = displayName ?: entity.displayName,
                logInType = (logInType ?: entity.logInType) as String,
                profileImage = profileImage?.toEntity() ?: entity.profileImage,
                inviteCode = inviteCode ?: entity.inviteCode
            )).toDomain()
        }
        return null
    }
}