package com.ccc.remind.presentation.ui.user

import com.ccc.remind.domain.entity.mind.ImageFile

data class UserProfileUiState(
    val displayName: String = "",
    val profileImage: ImageFile? = null,
    val originDisplayName: String = "",
    val originProfileImage: ImageFile? = null,
) {
    val isAnyEdited: Boolean
        get() = displayName != originDisplayName || profileImage?.id != originProfileImage?.id
}