package com.ccc.remind.data.source.remote.model.user

import com.ccc.remind.data.source.remote.model.mind.vo.ImageFileVO
import java.util.UUID

data class UserVO(
    val id: UUID,
    val displayName: String?,
    val profileImage: ImageFileVO?
)
