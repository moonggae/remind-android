package com.ccc.remind.presentation.ui

import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.entity.user.UserProfile

data class SharedUiState(
    val user: User? = null,
    val friend: UserProfile? = null,
    val isInitialized: Boolean = false
)