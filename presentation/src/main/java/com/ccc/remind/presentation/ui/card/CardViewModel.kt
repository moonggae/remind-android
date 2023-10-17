package com.ccc.remind.presentation.ui.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.usecase.mind.GetMindCardsUseCase
import com.ccc.remind.domain.usecase.mind.bookmark.DeleteMindCardBookmarkUseCase
import com.ccc.remind.domain.usecase.mind.bookmark.GetMindCardBookmarksUseCase
import com.ccc.remind.domain.usecase.mind.bookmark.PostMindCardBookmarkUseCase
import com.ccc.remind.presentation.ui.component.model.MindFilter
import com.ccc.remind.presentation.util.toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val getMindCards: GetMindCardsUseCase,
    private val getMindCardBookmarks: GetMindCardBookmarksUseCase,
    private val postMindCardBookmark: PostMindCardBookmarkUseCase,
    private val deleteMindCardBookmark: DeleteMindCardBookmarkUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CardUiState())
    val uiState: StateFlow<CardUiState> get() = this._uiState

    init {
        initMindCards()
    }

    private fun initMindCards() {
        viewModelScope.launch {
            getMindCards().combine(getMindCardBookmarks()) { mindCards, bookmarks ->
                Pair(mindCards, bookmarks)
            }.collect { (mindCards, bookmarkedCards) ->
                val bookmarkedCardIds = bookmarkedCards.map { it.id }
                val bookmarkedMindCards = mindCards.filter { card ->
                    card.id in bookmarkedCardIds
                }

                _uiState.update {
                    it.copy(
                        mindCards = mindCards,
                        bookmarkedMindCards = bookmarkedMindCards
                    )
                }
            }
        }
    }

    fun submitPostBookmark(cardId: Int) {
        viewModelScope.launch {
            postMindCardBookmark(cardId)
            updateBookmarkState(cardId) { state, card ->
                state.copy(
                    bookmarkedMindCards = state.bookmarkedMindCards + card
                )
            }
        }
    }

    fun submitDeleteBookmark(cardId: Int) {
        viewModelScope.launch {
            deleteMindCardBookmark(cardId)
            updateBookmarkState(cardId) { state, card ->
                state.copy(
                    bookmarkedMindCards = state.bookmarkedMindCards - card
                )
            }
        }
    }

    private fun updateBookmarkState(cardId: Int, updateFunction: (CardUiState, MindCard) -> CardUiState) {
        _uiState.value.mindCards.find { card ->
            card.id == cardId
        }?.let { card ->
            _uiState.update { state ->
                updateFunction(state, card)
            }
        }
    }


    fun updateMindCardFilter(filter: MindFilter) {
        when (filter) {
            MindFilter.ALL ->
                _uiState.update {
                    it.copy(
                        selectedMindFilters = listOf(MindFilter.ALL)
                    )
                }

            else ->
                _uiState.update {
                    it.copy(
                        selectedMindFilters = it.selectedMindFilters
                            .toMutableList()
                            .minus(MindFilter.ALL)
                            .toggle(filter)
                    )
                }
        }
    }
}