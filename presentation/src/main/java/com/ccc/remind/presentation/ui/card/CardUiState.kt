package com.ccc.remind.presentation.ui.card

import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.presentation.ui.component.model.MindFilter

data class CardUiState(
    val selectedMindFilters: List<MindFilter> = emptyList(),
    val bookmarkedMindCards: List<MindCard> = emptyList(),
    val mindCards: List<MindCard> = emptyList()
) {
    val filteredBookmarkedMindCards: List<MindCard>
        get() {
            val bookmarkedIds = bookmarkedMindCards.map { it.id }
            return filteredMindCards.filter { it.id in bookmarkedIds }
        }
    val filteredNotBookmarkedMindCards: List<MindCard>
        get() = filteredMindCards - bookmarkedMindCards.toSet()

    val filteredMindCards: List<MindCard>
        get() = mindCards // todo : 긍정, 부정, 보통 분류 후 작업
}