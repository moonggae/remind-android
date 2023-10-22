package com.ccc.remind.presentation.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.post.GetMindPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MindHistoryViewModel @Inject constructor(
    private val getMindPostList: GetMindPostListUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(MindHistoryUiState())
    val uiState: StateFlow<MindHistoryUiState> get() = _uiState

    private val isLoadingData = MutableStateFlow(false)


    init {
        submitGetMindPostList(0)
    }

    private fun submitGetMindPostList(page: Int) {
        if(_uiState.value.lastPage != null && page > _uiState.value.lastPage!!) return

        viewModelScope.launch {
            isLoadingData.update { true }

            getMindPostList(page).collect { postList ->
                _uiState.update { state ->
                    state.copy(
                        page = postList.page,
                        lastPage = postList.lastPage,
                        postMinds = _uiState.value.postMinds.plus(postList.data).distinct().sortedByDescending { it.id }
                    )
                }

                isLoadingData.emit(false)
            }
        }
    }

    fun loadNextPage() {
        if(isLoadingData.value) return
        submitGetMindPostList(_uiState.value.page + 1)
    }
}