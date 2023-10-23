package com.ccc.remind.presentation.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.icon.RoundedTextIcon
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.MindCard
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import kotlinx.coroutines.launch

@Composable
fun CardDetailScreen(
    navController: NavController = rememberNavController(),
    viewModel: CardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()

    var isBookmarked by remember { mutableStateOf(uiState.bookmarkedMindCards.contains(uiState.openedMindCard)) }

    var maxSize by remember { mutableStateOf(IntSize.Zero) }
    val similarCardItemSize = with(LocalDensity.current) { (maxSize.width.toDp() - 60.dp).div(other = 3) }

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.card_detail_appbar_title),
                navController = navController
            )
        },
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .onSizeChanged {
                scope.launch {
                    maxSize = it
                }
            }
    ) {
        uiState.openedMindCard?.let { openedMindCard ->
            val similarCards by remember { mutableStateOf(uiState.getSimilarCards(openedMindCard, 3)) }

            MindCard(
                mindCard = openedMindCard,
                isBookmarked = isBookmarked,
                showDisplayName = false,
                bookmarkSize = 40.dp,
                onClickBookmark = {
                    scope.launch {
                        if (isBookmarked) {
                            viewModel.submitDeleteBookmark(openedMindCard.id)
                            isBookmarked = false
                        } else {
                            viewModel.submitPostBookmark(openedMindCard.id)
                            isBookmarked = true
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(44.dp))

            Text(
                text = openedMindCard.displayName,
                style = RemindMaterialTheme.typography.bold_xxl,
                color = RemindMaterialTheme.colorScheme.fg_default
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
            ) {
                items(count = openedMindCard.tags.size) { index ->
                    RoundedTextIcon(text = openedMindCard.tags[index].displayName)
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            openedMindCard.description?.let { description ->
                Text(
                    text = stringResource(R.string.card_detail_label_description),
                    style = RemindMaterialTheme.typography.bold_xxl,
                    color = RemindMaterialTheme.colorScheme.fg_default
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description,
                    style = RemindMaterialTheme.typography.regular_lg,
                    color = RemindMaterialTheme.colorScheme.fg_muted
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            Text(
                text = stringResource(R.string.card_detail_label_similar_cards),
                style = RemindMaterialTheme.typography.bold_xxl,
                color = RemindMaterialTheme.colorScheme.fg_default
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(count = similarCards.size) { index ->
                    MindCard(
                        mindCard = similarCards[index],
                        onClick = { clickedCard ->
                            scope.launch {
                                navController.navigate("${Route.MindCard.Detail.name}/${clickedCard.id}")
                            }
                        },
                        cardSize = similarCardItemSize
                    )
                }
            }

            Row(
                modifier = Modifier.padding(top = 20.dp, bottom = 36.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "share mind card button",
                        modifier = Modifier.size(32.dp),
                        tint = RemindMaterialTheme.colorScheme.fg_subtle
                    )
                }

                PrimaryButton(text = stringResource(R.string.card_detail_button_post_this_mind_card)) {
                    scope.launch {
                        navController.navigate(route = "${Route.MindPost.SelectCard.name}?cardId=${openedMindCard.id}")
                    }
                }
            }
        }
    }
}