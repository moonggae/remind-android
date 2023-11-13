package com.ccc.remind.presentation.ui

import com.ccc.remind.domain.entity.user.CurrentUser
import com.ccc.remind.domain.entity.user.User

data class SharedUiState(
    val currentUser: CurrentUser? = null,
    val friend: User? = null,
    val isInitialized: Boolean = false
)