package com.ccc.remind.presentation.ui.card

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.layout.MindCardListGrid
import com.ccc.remind.presentation.ui.component.text.SecondaryText
import kotlinx.coroutines.launch

@Composable
fun CardBookmarkListScreen(
    navController: NavController,
    viewModel: CardViewModel
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.card_bookmark_list_appbar_title),
                navController = navController
            )
        }
    ) {
        if (uiState.filteredBookmarkedMindCards.isNotEmpty()) {
            MindCardListGrid(
                mindCards = uiState.filteredBookmarkedMindCards,
                bookmarkedMindCards = uiState.filteredBookmarkedMindCards,
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
        } else {
            SecondaryText(
                text = stringResource(R.string.card_bookmark_list_empty_text),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 120.dp)
            )
        }
    }
}