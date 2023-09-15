package com.ccc.remind.domain.entity.user

import com.ccc.remind.domain.entity.mind.ImageFile

data class UserProfile(
    val displayName: String?,
    val profileImage: ImageFile?,
    val inviteCode: String
)