package com.ccc.remind.presentation.ui.mindPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.usecase.GetMindCardsUseCase
import com.ccc.remind.presentation.ui.component.model.MindFilter
import com.ccc.remind.presentation.util.toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MindPostViewModel @Inject constructor(
    private val getMindCards: GetMindCardsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MindPostUiState(
            selectedMindFilters = listOf(MindFilter.ALL),
            mindCards = emptyList()
        )
    )

    val uiState: StateFlow<MindPostUiState>
        get() = _uiState


    init {
        initMindCards()
    }

    private fun initMindCards() {
        viewModelScope.launch {
            getMindCards().collect { mindCards ->
                _uiState.update {
                    it.copy(mindCards = mindCards)
                }
            }
        }
    }

    fun updateMindCardFilter(filter: MindFilter) {
        when(filter) {
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

    fun updateMindCard(mindCard: MindCard) {
        _uiState.update {
            it.copy(
                selectedMindCards = it.selectedMindCards.toggle(mindCard)
            )
        }
    }

    fun removeAllSelectedMindCards() {
        _uiState.update {
            it.copy(
                selectedMindCards = emptyList()
            )
        }
    }
}