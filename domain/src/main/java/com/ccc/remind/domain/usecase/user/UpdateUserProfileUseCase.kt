package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.repository.CurrentUserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val currentUserRepository: CurrentUserRepository
) {
    suspend operator fun invoke(
        displayName: String? = null,
        profileImage: ImageFile? = null
    ) = currentUserRepository.updateUserProfile(displayName, profileImage)
}