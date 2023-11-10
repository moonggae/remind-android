package com.ccc.remind.presentation.ui.mindPost

import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.presentation.ui.component.model.MindFilter

enum class PostViewType {
    FIRST_POST,
    DETAIL
}

data class MindPostUiState(
    val mindFilters:List<MindFilter> = emptyList(),
    val selectedMindFilters: List<MindFilter> = emptyList(),
    val mindCards: List<MindCard> = emptyList(),
    val selectedMindCards: List<MindCard> = emptyList(),
    val uploadedPhotos: List<ImageFile> = emptyList(),
    val memo: String? = null,
    val openedPost: MindPost? = null,
    val selectCardScreenBackStackEntryId: String? = null,
    val viewType: PostViewType = PostViewType.FIRST_POST
) {
    val filteredMindCards: List<MindCard>
        get() {
            return if(selectedMindFilters.contains(MindFilter.ALL)) mindCards
            else {
                var result = mindCards.toList()
                if(selectedMindFilters.contains(MindFilter.ENERGY_LOW))
                    result = result.filter { it.tags.find { cardTag ->
                        cardTag.tag.name == "energy" &&
                                cardTag.indicator != null &&
                                cardTag.indicator!! < 0
                    } != null }
                if(selectedMindFilters.contains(MindFilter.ENERGY_HIGH))
                    result = result.filter { it.tags.find { cardTag ->
                        cardTag.tag.name == "energy" &&
                                cardTag.indicator != null &&
                                cardTag.indicator!! > 0
                    } != null }
                if(selectedMindFilters.contains(MindFilter.PLEASANTNESS_LOW))
                    result = result.filter { it.tags.find { cardTag ->
                        cardTag.tag.name == "pleasantness" &&
                                cardTag.indicator != null &&
                                cardTag.indicator!! < 0
                    } != null }
                if(selectedMindFilters.contains(MindFilter.PLEASANTNESS_HIGH))
                    result = result.filter { it.tags.find { cardTag ->
                        cardTag.tag.name == "pleasantness" &&
                                cardTag.indicator != null &&
                                cardTag.indicator!! > 0
                    } != null }

                result
            }
        }

    val enabledCardListSubmit: Boolean
        get() = selectedMindCards.isNotEmpty()

    val selectedMindCardsMap: Map<MindCard, MindCardSelectType>
        get() = selectedMindCards.mapIndexed { index, mindCard ->
            mindCard to if (index == 0) MindCardSelectType.MAIN else MindCardSelectType.SUB
        }.toMap()
}