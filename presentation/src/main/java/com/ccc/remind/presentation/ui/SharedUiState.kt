package com.ccc.remind.presentation.ui

import com.ccc.remind.domain.entity.user.FriendUser
import com.ccc.remind.domain.entity.user.User

data class SharedUiState(
    val user: User? = null,
    val friend: FriendUser? = null
)