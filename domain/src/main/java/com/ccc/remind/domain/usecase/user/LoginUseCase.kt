package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.user.CurrentUser
import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.repository.AuthRepository
import com.ccc.remind.domain.repository.CurrentUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val currentUserRepository: CurrentUserRepository
) {
    suspend operator fun invoke(accessToken: String, logInType: LogInType): Flow<CurrentUser> = flow {
        lateinit var user: CurrentUser

        authRepository.login(accessToken, logInType).collect { jwtToken ->
            user = CurrentUser(
                accessToken = jwtToken.accessToken,
                refreshToken = jwtToken.refreshToken,
                displayName = null,
                logInType = logInType,
                profileImage = null,
                inviteCode = ""
            )
        }

        currentUserRepository.replaceLoggedInUser(user)

        currentUserRepository.getUserProfile().collect { profile ->
            user = user.copy(
                displayName = profile.displayName,
                profileImage = profile.profileImage,
                inviteCode = profile.inviteCode
            )
        }

        currentUserRepository.replaceLoggedInUser(user)

        emit(user)
    }
}