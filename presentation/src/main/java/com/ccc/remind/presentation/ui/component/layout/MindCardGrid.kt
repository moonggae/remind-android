package com.ccc.remind.presentation.ui.component.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.MindCard

private object MindCardGridDefault {
    val columns: GridCells = GridCells.Adaptive(100.dp)
    val verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp)
    val horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(10.dp)
    val columnHorizontalAlignment = Alignment.CenterHorizontally
    val columnVerticalArrangement = Arrangement.spacedBy(8.dp)
}

@Composable
fun MindCardPostGrid(
    modifier: Modifier = Modifier,
    columns: GridCells = MindCardGridDefault.columns,
    verticalArrangement: Arrangement.Vertical = MindCardGridDefault.verticalArrangement,
    horizontalArrangement: Arrangement.Horizontal = MindCardGridDefault.horizontalArrangement,
    mindCards: List<MindCard>,
    selectedMindCards: List<MindCard>,
    onClickMindCard: (MindCard) -> Unit
) {
    val sortedItems: List<MindCard> = (selectedMindCards.plus(mindCards)).distinctBy { it.id }

    LazyVerticalGrid(
        columns = columns,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        modifier = modifier
    ) {
        items(sortedItems.size) { index ->
            Column(
                horizontalAlignment = MindCardGridDefault.columnHorizontalAlignment,
                verticalArrangement = MindCardGridDefault.columnVerticalArrangement
            ) {
                val item = sortedItems[index]
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

@Composable
fun MindCardListGrid(
    modifier: Modifier = Modifier,
    columns: GridCells = MindCardGridDefault.columns,
    verticalArrangement: Arrangement.Vertical = MindCardGridDefault.verticalArrangement,
    horizontalArrangement: Arrangement.Horizontal = MindCardGridDefault.horizontalArrangement,
    mindCards: List<MindCard>,
    bookmarkedMindCards: List<MindCard>,
    isBookmarkHighlight: Boolean = true,
    onClickMindCard: (MindCard) -> Unit,
    onClickBookmark: (MindCard) -> Unit
) {
    val sortedCards =
        if(isBookmarkHighlight) bookmarkedMindCards + (mindCards - bookmarkedMindCards.toSet())
        else mindCards

    LazyVerticalGrid(
        columns = columns,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        modifier = modifier
    ) {
        items(sortedCards.size) { index ->
            Column(
                horizontalAlignment = MindCardGridDefault.columnHorizontalAlignment,
                verticalArrangement = MindCardGridDefault.columnVerticalArrangement
            ) {
                val item = sortedCards[index]
                val isBookMarked = bookmarkedMindCards.contains(item)
                MindCard(
                    mindCard = item,
                    onClick = onClickMindCard,
                    isBookmarked = isBookMarked,
                    onClickBookmark = onClickBookmark
                )
            }
        }
    }
}