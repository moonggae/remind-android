package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.user.CurrentUser
import com.ccc.remind.domain.repository.CurrentUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLoggedInUserUserCase @Inject constructor(private val currentUserRepository: CurrentUserRepository) {
    private var loggedInUser: CurrentUser? = null
    suspend operator fun invoke() : Flow<CurrentUser?> = flow {
        currentUserRepository.getLoggedInUser().collect { localUser ->
            loggedInUser = localUser
        }

        loggedInUser?.let {
            currentUserRepository.getUserProfile().collect { profile ->
                loggedInUser = currentUserRepository.updateLocalUser(
                    displayName = profile.displayName,
                    profileImage = profile.profileImage,
                    inviteCode = profile.inviteCode
                )
            }
        }

        emit(loggedInUser)
    }
}