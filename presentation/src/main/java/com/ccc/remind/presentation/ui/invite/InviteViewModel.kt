package com.ccc.remind.presentation.ui.invite

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.presentation.util.Constants.BASE_URL
import com.ccc.remind.presentation.util.Constants.INVITE_CODE_LENGTH
import com.ccc.remind.presentation.util.Constants.INVITE_URL_PATH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(InviteUiState())

    val uiState: StateFlow<InviteUiState>
        get() = _uiState

    fun updateInputInviteCode(text: String) {
        val inviteUrlPrefix = "${BASE_URL}/${INVITE_URL_PATH}/"
        var pastedCode: Int? = null
        if(
            text.startsWith(inviteUrlPrefix) &&
            text.length >= inviteUrlPrefix.length + INVITE_CODE_LENGTH &&
            text.substring(inviteUrlPrefix.length, inviteUrlPrefix.length + INVITE_CODE_LENGTH).isDigitsOnly()
        ) {
            pastedCode = text
                .substring(inviteUrlPrefix.length, inviteUrlPrefix.length + INVITE_CODE_LENGTH)
                .toIntOrNull()
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    inputInviteCode = pastedCode?.toString() ?: text
                )
            }
        }
    }
}