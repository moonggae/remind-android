package com.ccc.remind.domain.usecase.friend

import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.domain.repository.FriendRepository
import javax.inject.Inject

class GetReceivedFriendRequestsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    operator fun invoke() = friendRepository.getReceivedFriendRequests()

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
        ReceivedFriendRequest(
            id = 200000,
            requestUser = mockUserProfiles[0]
        ),
        ReceivedFriendRequest(
            id = 200000,
            requestUser = mockUserProfiles[1]
        )
    )
}