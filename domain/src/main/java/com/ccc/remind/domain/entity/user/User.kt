package com.ccc.remind.domain.entity.user

import com.ccc.remind.domain.entity.mind.ImageFile
import java.util.UUID

data class User(
    val id: UUID,
    val displayName: String?,
    val profileImage: ImageFile?,
    val inviteCode: String
)