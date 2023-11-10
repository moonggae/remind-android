package com.ccc.remind.presentation.ui.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.layout.MindCardListGrid
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.MindCardListFilterBar
import kotlinx.coroutines.launch


@Composable
@Preview(showBackground = true)
fun CardListScreenPreview() {
    CardListScreen()
}

@Composable
fun CardListScreen(
    navController: NavController = rememberNavController(),
    viewModel: CardViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()
    val bookMarkIconButton: @Composable (() -> Unit) = {
        IconButton(
            onClick = { scope.launch { navController.navigate(Route.MindCard.BookmarkList.name) } },
            modifier = Modifier.size(40.dp)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = null
            )
        }
    }

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.card_list_appbar_title),
                enableBack = false,
                suffix = { bookMarkIconButton() }
            )
        }
    ) {
        MindCardListFilterBar(
            filterValues = uiState.mindFilters,
            selectedFilters = uiState.selectedMindFilters,
            onClickFilter = viewModel::updateMindCardFilter,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(21.dp))

        MindCardListGrid(
            mindCards = uiState.filteredMindCards,
            bookmarkedMindCards = uiState.filteredBookmarkedMindCards,
            onClickMindCard = { clickedCard ->
                scope.launch {
                    navController.navigate("${Route.MindCard.Detail.name}/${clickedCard.id}")
                }
            },
            onClickBookmark = { card ->
                scope.launch {
                    if (uiState.bookmarkedMindCards.contains(card)) {
                        viewModel.submitDeleteBookmark(card.id)
                    } else {
                        viewModel.submitPostBookmark(card.id)
                    }
                }
            }
        )
    }
}