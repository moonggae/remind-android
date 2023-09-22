package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.repository.FriendRepository
import javax.inject.Inject

class GetFriendRequestsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    operator fun invoke() = friendRepository.getFriendRequests()
}