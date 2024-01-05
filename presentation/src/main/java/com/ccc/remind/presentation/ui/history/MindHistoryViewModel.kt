package com.ccc.remind.presentation.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.setting.HistoryViewType
import com.ccc.remind.domain.usecase.friend.GetFriendUseCase
import com.ccc.remind.domain.usecase.post.GetMindPostListUseCase
import com.ccc.remind.domain.usecase.setting.GetHistoryViewTypeUseCase
import com.ccc.remind.domain.usecase.setting.UpdateHistoryViewTypeUseCase
import com.ccc.remind.domain.usecase.user.GetLoggedInUserUserCase
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
    private val getViewType: GetHistoryViewTypeUseCase,
    private val updateViewType: UpdateHistoryViewTypeUseCase,
    private val getFriend: GetFriendUseCase,
    private val getLoggedInUser: GetLoggedInUserUserCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MindHistoryUiState())
    val uiState: StateFlow<MindHistoryUiState> get() = _uiState

    private val isLoadingData = MutableStateFlow(false)

    private val mutex = Mutex()

    init {
        getMindPostList.initObserver(viewModelScope)
        initViewType()
        initCurrentUser()
        initFriend()
    }

    private fun initMindPostList() {
        viewModelScope.launch {
            isLoadingData.update { true }
            getMindPostList.clearCache()
            getMindPostList().collectLatest { posts ->
                _uiState.update { uiState ->
                    uiState.copy(
                        posts = posts.sortedByDescending { it.createdAt }
                    )
                }
                isLoadingData.update { false }
            }
        }
    }

    private fun initViewType() {
        viewModelScope.launch {
            getViewType().collect { viewType ->
                _uiState.update {
                    it.copy(
                        viewType = viewType
                    )
                }
            }
        }
    }

    private fun initCurrentUser() {
        viewModelScope.launch {
            getLoggedInUser().collectLatest { user ->
                _uiState.update {
                    it.copy(
                        currentUser = user
                    )
                }
            }
        }
    }

    private fun initFriend() {
        viewModelScope.launch {
            getFriend.friendStateFlow.collectLatest { friend ->
                _uiState.update {
                    it.copy(
                        friend = friend
                    )
                }
                initMindPostList()
            }
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            mutex.withLock {
                if (isLoadingData.value || _uiState.value.isLastPage) return@withLock
                isLoadingData.value = true
            }

            getMindPostList.next()

            isLoadingData.value = false
        }
    }

    fun changeViewType(type: HistoryViewType) {
        viewModelScope.launch {
            updateViewType(type)
            _uiState.update {
                it.copy(
                    viewType = type
                )
            }
        }
    }

    fun selectCalendarDay(day: CalendarDay) {
        if (_uiState.value.selectedDay == day) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedDay = day
                )
            }
        }
    }

    fun changeCalendarMonth(calendarMonth: CalendarMonth) {
        if (_uiState.value.isLastPage) return

        val lastItemMonth = _uiState.value.posts.lastOrNull()?.createdAt?.toLocalDate()?.yearMonth

        if(lastItemMonth == null || lastItemMonth >= calendarMonth.yearMonth) {
            loadNextPage()
        }
    }
}