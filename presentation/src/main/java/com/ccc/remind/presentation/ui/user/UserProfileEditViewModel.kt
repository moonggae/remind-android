package com.ccc.remind.presentation.ui.user

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.PostImagesUseCase
import com.ccc.remind.domain.usecase.user.DeleteAccountUseCase
import com.ccc.remind.domain.usecase.user.GetMyProfileUseCase
import com.ccc.remind.domain.usecase.user.UpdateUserProfileUseCase
import com.ccc.remind.presentation.MyApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class UserProfileEditViewModel @Inject constructor(
    private val getMyProfile: GetMyProfileUseCase,
    private val postImages: PostImagesUseCase,
    private val updateUserProfile: UpdateUserProfileUseCase,
    private val deleteAccount: DeleteAccountUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState>
        get() = _uiState

    fun initUserProfile() {
        viewModelScope.launch {
            getMyProfile().collect { nullableProfile ->
                nullableProfile?.let { profile ->
                    _uiState.update {
                        it.copy(
                            displayName = profile.displayName ?: "",
                            originDisplayName = profile.displayName ?: "",
                            profileImage = profile.profileImage,
                            originProfileImage = profile.profileImage
                        )
                    }
                }
            }
        }
    }

    fun updateDisplayName(text: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    displayName = text,
                )
            }
        }
    }

    fun updateProfileImage(uri: Uri?) {
        if(uri == null) return
        viewModelScope.launch {
            val uploadedImage = postImages(MyApplication.applicationContext() ,listOf(uri)).first()
            _uiState.update {
                it.copy(
                    profileImage = uploadedImage,
                )
            }
        }
    }

    fun submitUpdateUserProfile() {
        if(!_uiState.value.isAnyEdited) return
        viewModelScope.launch {
            runBlocking {
                updateUserProfile(
                    displayName = _uiState.value.displayName,
                    profileImage = _uiState.value.profileImage
                )
            }
        }
    }

    fun submitDeleteAccount() {
        viewModelScope.launch {
            deleteAccount()
        }
    }
}