package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.FriendRepository
import java.util.UUID
import javax.inject.Inject

class GetReceivedFriendRequestsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    operator fun invoke() = friendRepository.getReceivedFriendRequests()

    private val mockUsers = listOf(
        User(
            displayName = "똑똑한나무젓가락",
            profileImage = null,
            inviteCode = "100000",
            id = UUID.randomUUID()
        ),
        User(
            displayName = "뜨끈한냉면",
            profileImage = null,
            inviteCode = "100001",
            id = UUID.randomUUID()
        )
    )

    val mock = listOf(
        ReceivedFriendRequest(
            id = 200000,
            requestUser = mockUsers[0]
        ),
        ReceivedFriendRequest(
            id = 200000,
            requestUser = mockUsers[1]
        )
    )
}