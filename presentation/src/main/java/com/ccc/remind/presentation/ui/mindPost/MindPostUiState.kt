package com.ccc.remind.presentation.ui.mindPost

import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.presentation.ui.component.model.MindFilter

data class MindPostUiState(
    val selectedMindFilters: List<MindFilter> = emptyList(),
    val mindCards: List<MindCard> = emptyList(),
    val selectedMindCards: List<MindCard> = emptyList(),
    val uploadedPhotos: List<ImageFile> = emptyList(),
    val memo: String? = null,
    val postedMind: MindPost? = null
) {
    val filteredMindCards : List<MindCard>
        get() = mindCards // todo : 긍정, 부정, 보통 분류 후 작업

    val enabledCardListSubmit : Boolean
        get() = selectedMindCards.isNotEmpty()

}