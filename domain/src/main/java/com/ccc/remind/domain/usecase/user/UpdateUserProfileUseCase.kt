package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        displayName: String? = null,
        profileImage: ImageFile? = null
    ) = userRepository.updateUserProfile(displayName, profileImage)
}