package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.repository.FriendRepository
import javax.inject.Inject

class CancelFriendRequestUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(requestId: Int) = friendRepository.cancelFriendRequest(requestId)
}