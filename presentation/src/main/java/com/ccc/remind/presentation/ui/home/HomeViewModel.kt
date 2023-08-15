package com.ccc.remind.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.GetLastMindUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLastMind: GetLastMindUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiStatus())

    val uiState: StateFlow<HomeUiStatus>
        get() = _uiState

    init {
        refreshLastPostedMind()
    }

    fun refreshLastPostedMind() {
        viewModelScope.launch {
            getLastMind().collect { lastMind ->
                _uiState.update {
                    it.copy(
                        lastPostedMind = lastMind
                    )
                }
            }
        }
    }
}