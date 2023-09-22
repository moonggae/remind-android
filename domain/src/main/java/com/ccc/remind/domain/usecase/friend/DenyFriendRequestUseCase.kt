package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.repository.FriendRepository
import javax.inject.Inject

class DenyFriendRequestUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(requestId: Int) = friendRepository.denyFriendRequest(requestId)
}