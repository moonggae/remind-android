package com.ccc.remind.domain.entity.user

import com.ccc.remind.domain.entity.mind.ImageFile
import java.util.UUID

data class PostUser(
    val id: UUID,
    val displayName: String?,
    val profileImage: ImageFile?
)