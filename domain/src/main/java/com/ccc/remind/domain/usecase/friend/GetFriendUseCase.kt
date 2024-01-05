package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.FriendRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    val friendStateFlow = friendRepository.friendFlow
    suspend operator fun invoke(scope: CoroutineScope): Flow<User?> {
        friendRepository.observeSocket(scope)
        return friendRepository.getFriend()
    }
}