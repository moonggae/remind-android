package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.domain.repository.FriendRepository
import javax.inject.Inject

class GetFriendRequestsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    operator fun invoke() = friendRepository.getFriendRequests()

    private val mockUserProfiles = listOf(
        UserProfile(
            displayName = "똑똑한나무젓가락",
            profileImage = null,
            inviteCode = "100000"
        ),
        UserProfile(
            displayName = "뜨끈한냉면",
            profileImage = null,
            inviteCode = "100001"
        )
    )

    val mock = listOf(
        FriendRequest(
            id = 100000,
            receivedUser = mockUserProfiles[0]
        ),
        FriendRequest(
            id = 100001,
            receivedUser = mockUserProfiles[1]
        )
    )
}