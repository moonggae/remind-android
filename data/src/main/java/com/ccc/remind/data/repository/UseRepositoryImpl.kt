package com.ccc.remind.data.repository

import android.util.Log
import com.ccc.remind.data.mapper.toData
import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.mapper.toEntity
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.local.model.UserEntity
import com.ccc.remind.data.source.remote.UserRemoteService
import com.ccc.remind.data.source.remote.model.user.DisplayNameDto
import com.ccc.remind.data.source.remote.model.user.UpdateFCMTokenRequestDto
import com.ccc.remind.data.source.remote.model.user.UserProfileUpdateDto
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteService: UserRemoteService
) : UserRepository {
    companion object {
        private const val TAG = "UseRepositoryImpl"
    }
    
    override suspend fun getLoggedInUser(): Flow<User?> = flow {
        val user = userLocalDataSource.fetchLoggedInUser()?.toDomain()
        Log.d(TAG, "UserRepositoryImpl - getLoggedInUser - user: ${user}")
        emit(user)
    }

    override suspend fun replaceLoggedInUser(user: User) {
        userLocalDataSource.deleteLoggedInUser()
        userLocalDataSource.postLoggedInUser(user.toData())
    }

    override suspend fun deleteLoggedInUser() {
        userLocalDataSource.deleteLoggedInUser()
    }

    override fun getUserProfile(): Flow<UserProfile> = flow {
        val profile: UserProfile = userRemoteService.fetchUserProfile().body()!!.toDomain()
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
    ) {
        val entity = userLocalDataSource.fetchLoggedInUser()
        if(entity != null) {
            userLocalDataSource.deleteLoggedInUser()
            userLocalDataSource.postLoggedInUser(
                UserEntity(
                    accessToken = accessToken ?: entity.accessToken,
                    refreshToken = refreshToken ?: entity.refreshToken,
                    displayName = displayName ?: entity.displayName,
                    logInType = (logInType ?: entity.logInType) as String,
                    profileImage = profileImage?.toEntity() ?: entity.profileImage,
                    inviteCode = inviteCode ?: entity.inviteCode
            ))
        }
    }
}