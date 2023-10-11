package com.ccc.remind.data.source.remote.model.user

import com.ccc.remind.data.source.remote.model.mind.vo.ImageFileVO

data class UserProfileVO(
    val displayName: String?,
    val profileImage: ImageFileVO?,
    val inviteCode: String
)