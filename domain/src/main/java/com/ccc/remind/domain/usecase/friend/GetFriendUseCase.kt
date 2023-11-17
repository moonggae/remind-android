package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.repository.FriendRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    val friend = friendRepository.friendFlow
    suspend fun initObserver(scope: CoroutineScope) = friendRepository.observeSocket(scope)
}