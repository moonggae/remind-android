package com.ccc.remind.presentation.ui.component.mindPost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.presentation.ui.component.model.MindCardSelectType

@Composable
fun MindCardGrid(
    modifier: Modifier = Modifier,
    columns: GridCells = GridCells.Adaptive(100.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(10.dp),
    mindCards: List<MindCard>,
    selectedMindCards: List<MindCard>,
    onClickMindCard: (MindCard) -> Unit
) {
    LazyVerticalGrid(
        columns = columns,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        modifier = modifier
    ) {
        items(mindCards.size) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val item = mindCards[index]
                val selectType = selectedMindCards.indexOf(item).let { index ->
                    when {
                        index == 0 -> MindCardSelectType.MAIN
                        index > 0 -> MindCardSelectType.SUB
                        else -> null
                    }
                }
                MindCard(
                    mindCard = item,
                    onClick = onClickMindCard,
                    selectType = selectType
                )
            }
        }
    }
}