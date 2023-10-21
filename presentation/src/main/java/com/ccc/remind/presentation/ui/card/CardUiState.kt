package com.ccc.remind.presentation.ui.card

import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.presentation.ui.component.model.MindFilter
import kotlin.math.pow

data class CardUiState(
    val selectedMindFilters: List<MindFilter> = emptyList(),
    val bookmarkedMindCards: List<MindCard> = emptyList(),
    val mindCards: List<MindCard> = emptyList(),
    val openedMindCard: MindCard? = null,
    val detailCardIds: List<Int> = emptyList()
) {
    val filteredBookmarkedMindCards: List<MindCard>
        get() {
            val bookmarkedIds = bookmarkedMindCards.map { it.id }
            return filteredMindCards.filter { it.id in bookmarkedIds }
        }

    fun getSimilarCards(mindCard: MindCard, count: Int): List<MindCard> {
        val compareCards = mindCards - mindCard
        val (energy, pleasantness) = mindCard.getIndicators() ?: return emptyList()

        val part = classifyPart(energy, pleasantness)

        return compareCards.filter { card ->
            val (cardVitality, cardComfort) = card.getIndicators() ?: return@filter false
            classifyPart(cardVitality, cardComfort) == part
        }.sortedBy {
            val (cardEnergy, cardPleasantness) = it.getIndicators() ?: return@sortedBy Double.MAX_VALUE
            ((energy - cardEnergy).pow(2) + (pleasantness - cardPleasantness).pow(2))
        }.take(count)
    }

    private fun MindCard.getIndicators(): Pair<Double, Double>? {
        val energy = tags.find { it.tag.name == "energy" }?.indicator ?: return null
        val pleasantness = tags.find { it.tag.name == "pleasantness" }?.indicator ?: return null
        return Pair(energy, pleasantness)
    }

    private fun classifyPart(vitality: Double, comfort: Double): Int {
        return when {
            vitality < 0 && comfort < 0 -> 1
            vitality < 0 && comfort > 0 -> 2
            vitality > 0 && comfort < 0 -> 3
            vitality > 0 && comfort > 0 -> 4
            else -> 0
        }
    }

    val filteredMindCards: List<MindCard>
        get() = mindCards // todo : 긍정, 부정, 보통 분류 후 작업
}