package com.ccc.remind.presentation.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.domain.entity.setting.HistoryViewType
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.history.ItemListTypeSelector
import com.ccc.remind.presentation.ui.component.pageComponent.history.MindListTimestampType
import com.ccc.remind.presentation.ui.component.pageComponent.history.calendar.MindPostCalendarView
import com.ccc.remind.presentation.ui.component.pageComponent.history.list.PostDateLabel
import com.ccc.remind.presentation.ui.component.pageComponent.history.list.PostMindListView
import com.ccc.remind.presentation.ui.component.text.SecondaryText
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.toZonedDateTime


@Composable
@Preview(showBackground = true)
fun MindHistoryScreenPreview() {
    MindHistoryScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindHistoryScreen(
    navController: NavController = rememberNavController(),
    viewModel: MindHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listViewScrollState = rememberLazyListState()
    val density = LocalDensity.current

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
                        onTypeChanged = viewModel::changeViewType
                    )
                }
            )
        },
        padding = PaddingValues(0.dp)
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
                    scrollState = listViewScrollState,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                HistoryViewType.CALENDAR -> {
                    MindPostCalendarView(
                        selectedDay = uiState.selectedDay,
                        postMinds = uiState.postMinds,
                        onClickDay = viewModel::selectCalendarDay,
                        onChangeMonth = viewModel::changeCalendarMonth,
                        modifier = Modifier.padding(horizontal = 44.5.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Surface(
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp
                        ),
                        color = RemindMaterialTheme.colorScheme.bg_subtle,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            Modifier
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                    top = 16.dp
                                )
                                .fillMaxHeight()
                        ) {
                            PostDateLabel(
                                createdAt = uiState.selectedDay.date.toZonedDateTime(),
                                style = RemindMaterialTheme.typography.bold_xl
                            )

                            if(uiState.selectedDayPostMinds.isNotEmpty()) {
                                PostMindListView(
                                    posts = uiState.selectedDayPostMinds,
                                    navController = navController,
                                    scrollState = rememberLazyListState(),
                                    timestampType = MindListTimestampType.EVERYTIME,
                                    timestampFormat = "a h:mm",
                                    itemBackgroundColor = RemindMaterialTheme.colorScheme.bg_default,
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                )
                            } else {
                                SecondaryText(
                                    text = stringResource(R.string.mind_history_list_empty_text),
                                    modifier = Modifier
                                        .align(CenterHorizontally)
                                        .padding(top = 64.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}