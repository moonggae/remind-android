package com.ccc.remind.presentation.ui.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.layout.MindCardListGrid
import kotlinx.coroutines.launch

@Composable
fun CardListDetailScreen(
    navController: NavController,
    viewModel: CardViewModel
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.card_list_detail_appbar_title),
                navController = navController
            )
        }
    ) {
        MindCardListGrid(
            mindCards = uiState.detailCardIds.mapNotNull { carId -> uiState.mindCards.find { it.id == carId } },
            bookmarkedMindCards = uiState.filteredBookmarkedMindCards,
            isBookmarkHighlight = false,
            onClickMindCard = { clickedCard ->
                scope.launch {
                    navController.navigate("${Route.MindCard.Detail.name}/${clickedCard.id}")
                }
            },
            onClickBookmark = { card ->
                scope.launch {
                    viewModel.submitDeleteBookmark(card.id)
                }
            }
        )
    }
}