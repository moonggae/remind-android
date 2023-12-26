package com.ccc.remind.presentation.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.friend.GetFriendUseCase
import com.ccc.remind.domain.usecase.post.GetMindPostListUseCase
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.yearMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class MindHistoryViewModel @Inject constructor(
    private val getMindPostList: GetMindPostListUseCase,
    private val getFriend: GetFriendUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MindHistoryUiState())
    val uiState: StateFlow<MindHistoryUiState> get() = _uiState

    private val isLoadingData = MutableStateFlow(false)

    private val mutex = Mutex()

    init {
        getMindPostList.initObserver(viewModelScope)
        initMindPostList()
        observeFriendState()
    }

    private fun initMindPostList() {
        viewModelScope.launch {
            isLoadingData.update { true }
            getMindPostList.clearCache()
            getMindPostList.get().collectLatest { newPosts ->
                _uiState.update {
                    it.copy(
                        postMinds = newPosts
                    )
                }
                isLoadingData.update { false }
            }
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            mutex.withLock {
                if (isLoadingData.value || _uiState.value.isLastPage) return@withLock
                isLoadingData.value = true
            }

            val dataUpdated = getMindPostList.next()

            if (dataUpdated) {
                getMindPostList.posts.replayCache.lastOrNull()?.let { newPosts ->
                    _uiState.update { it.copy(postMinds = newPosts) }
                }
            } else {
                _uiState.update { it.copy(isLastPage = true) }
            }

            isLoadingData.value = false
        }
    }

    private fun observeFriendState() {
        viewModelScope.launch {
            getFriend.friend.collect {
                initMindPostList()
            }
        }
    }

    fun updateViewType(type: HistoryViewType) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    viewType = type
                )
            }
        }
    }

    fun selectCalendarDay(day: CalendarDay) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedDay = if (it.selectedDay == day) null else day
                )
            }
        }
    }

    fun changeCalendarMonth(calendarMonth: CalendarMonth) {
        if (_uiState.value.isLastPage) return

        val lastItemMonth = _uiState.value.postMinds.lastOrNull()?.createdAt?.toLocalDate()?.yearMonth

        if(lastItemMonth == null || lastItemMonth >= calendarMonth.yearMonth) {
            loadNextPage()
        }
    }
}