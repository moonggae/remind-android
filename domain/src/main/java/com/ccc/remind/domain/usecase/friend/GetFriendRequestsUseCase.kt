package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.FriendRepository
import java.util.UUID
import javax.inject.Inject

class GetFriendRequestsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    operator fun invoke() = friendRepository.getFriendRequests()

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
        FriendRequest(
            id = 100000,
            receivedUser = mockUsers[0]
        ),
        FriendRequest(
            id = 100001,
            receivedUser = mockUsers[1]
        )
    )
}