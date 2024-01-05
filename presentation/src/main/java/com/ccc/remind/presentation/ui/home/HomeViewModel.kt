package com.ccc.remind.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.post.RequestFriendMindUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val requestFriendMind: RequestFriendMindUseCase,
) : ViewModel() {

    fun submitRequestFriendMind() {
        // TODO: exception 처리
        viewModelScope.launch {
            requestFriendMind()
        }
    }
}