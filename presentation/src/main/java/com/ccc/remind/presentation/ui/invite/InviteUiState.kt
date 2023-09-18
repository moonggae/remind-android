package com.ccc.remind.presentation.ui.invite

import androidx.core.text.isDigitsOnly
import com.ccc.remind.presentation.util.Constants

data class InviteUiState(
    val inputInviteCode: String = ""
) {
    val validInviteCode: Boolean
        get() = inputInviteCode.isDigitsOnly() && inputInviteCode.length == Constants.INVITE_CODE_LENGTH
}