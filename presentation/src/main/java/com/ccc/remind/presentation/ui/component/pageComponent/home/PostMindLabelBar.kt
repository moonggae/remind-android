package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.ccc.remind.domain.entity.mind.MindPostCard
import com.ccc.remind.presentation.ui.component.icon.RoundedTextIcon

@Composable
fun PostMindLabelBar(
    cards: List<MindPostCard>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
        items(
            count = cards.size
        ) { index ->
            RoundedTextIcon(
                text = cards[index].card.displayName
            )
        }
    }
}