package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userProfile: UserProfile) = userRepository.updateUserProfile(userProfile)
}