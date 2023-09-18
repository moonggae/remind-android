package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInviteUserProfileUseCase @Inject constructor(private val friendRepository: FriendRepository) {
    operator fun invoke(inviteCode: String): Flow<UserProfile> = friendRepository.getProfile(inviteCode)
}