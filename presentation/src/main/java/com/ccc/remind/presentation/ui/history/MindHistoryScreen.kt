package com.ccc.remind.presentation.ui.history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.history.ItemListTypeSelector
import com.ccc.remind.presentation.ui.component.pageComponent.history.calendar.MindPostCalendarView
import com.ccc.remind.presentation.ui.component.pageComponent.history.list.PostMindListView
import com.ccc.remind.presentation.ui.component.text.SecondaryText


@Composable
@Preview(showBackground = true)
fun MindHistoryScreenPreview() {
    MindHistoryScreen()
}

@Composable
fun MindHistoryScreen(
    navController: NavController = rememberNavController(),
    viewModel: MindHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listViewScrollState = rememberLazyListState()

    LaunchedEffect(listViewScrollState.firstVisibleItemIndex) {
        if (!uiState.isLastPage) {
            val scrollPercent = listViewScrollState.firstVisibleItemIndex / listViewScrollState.layoutInfo.totalItemsCount.toDouble()
            if (scrollPercent >= 0.5) {
                viewModel.loadNextPage()
            }
        }
    }


    BasicScreen(
        appBar = {
            AppBar(
                navController = navController,
                title = stringResource(R.string.mind_history_appbar_title),
                suffix = {
                    ItemListTypeSelector(
                        selectedType = uiState.viewType,
                        onTypeChanged = viewModel::updateViewType
                    )
                }
            )
        }
    ) {
        if (uiState.postMinds.isEmpty()) {
            SecondaryText(
                text = stringResource(R.string.mind_history_list_empty_text),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 120.dp)
            )
        } else {
            when (uiState.viewType) {
                HistoryViewType.LIST -> PostMindListView(
                    posts = uiState.postMinds,
                    navController = navController,
                    scrollState = listViewScrollState
                )

                HistoryViewType.CALENDAR -> {
                    MindPostCalendarView(
                        selectedDay = uiState.selectedDay,
                        postMinds = uiState.postMinds,
                        onClickDay = viewModel::selectCalendarDay,
                        onChangeMonth = viewModel::changeCalendarMonth
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))

                    PostMindListView(
                        posts = uiState.selectedDayPostMinds,
                        navController = navController,
                        scrollState = rememberLazyListState(),
                        showDate = false
                    )
                }
            }
        }
    }
}