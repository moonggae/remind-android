package com.ccc.remind.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BackgroundContainer
import com.ccc.remind.presentation.ui.component.icon.RoundedTextIcon
import com.ccc.remind.presentation.ui.component.mindPost.ViewDetailTextButton
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.Constants.POST_MIND_RESULT_KEY
import com.ccc.remind.presentation.util.buildCoilRequest
import kotlinx.coroutines.launch

/*
todo
background color
refresh token
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sharedUiState by sharedViewModel.uiState.collectAsState()

    addOnPostMindResult(navController = navController) {
        viewModel.refreshLastPostedMind()
    }

    Column(
        modifier = Modifier
            .padding(
                horizontal = 20.dp,
                vertical = 28.dp
            )
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(id = R.string.home_title_greeting, sharedUiState.user?.displayName ?: "유저"),
                style = RemindMaterialTheme.typography.bold_xxl,
                modifier = Modifier.padding(top = 40.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                IconButton(onClick = {
                    // todo
                }, modifier = Modifier.size(40.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_notification_off),
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = {
                        // todo
                    },
                    modifier = Modifier.size(40.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        if (uiState.lastPostedMind == null) {
            EmptyPostMindCard(
                userDisplayName = sharedUiState.user?.displayName ?: "유저",
                onClickAddButton = { navController.navigate(Route.MindPost.CardList.name) }
            )
        } else {
            AsyncImage(
                model = buildCoilRequest(
                    context = LocalContext.current,
                    url = uiState.lastPostedMind?.cards?.first()?.card?.imageUrl ?: ""
                ),
                contentDescription = "",
                modifier = Modifier
                    .width(153.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth,
            )

            Spacer(modifier = Modifier.height(18.dp))

            PrimaryButton(
                text = "감정 기록하기",
                textStyle = RemindMaterialTheme.typography.bold_lg
                    .copy(color = RemindMaterialTheme.colorScheme.bg_default),
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .height(46.dp)
            ) { navController.navigate(Route.MindPost.CardList.name) }
        }


        Spacer(modifier = Modifier.height(26.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.home_label_mind_history),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            ViewDetailTextButton {
                // todo
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.lastPostedMind == null) {
            EmptyPostMindLabelCard()
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
            ) {
                items(
                    count = uiState.lastPostedMind?.cards?.size ?: 0
                ) { index ->
                    RoundedTextIcon(
                        text = uiState.lastPostedMind?.cards?.get(index)?.card?.displayName ?: ""
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(26.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.home_label_mind_memo),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            CompositionLocalProvider( // TextButton padding 제거
                LocalMinimumInteractiveComponentEnforcement provides false
            ) {
                IconButton(
                    onClick = { /*TODO*/
                        navController.navigate(route = "${Route.MemoEdit.name}/${uiState.lastPostedMind?.id}/${uiState.lastPostedMind?.memo?.id ?: -1}")
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_light),
                        contentDescription = stringResource(id = R.string.arrow_light),
                        tint = Color(0xFF686868),
                        modifier = Modifier.size(16.dp)
                    )
                }

            }
        }


        when {
            uiState.lastPostedMind == null -> EmptyMemoCard()
            uiState.lastPostedMind?.memo == null -> EmptyMemoCard(
                onClickAddButton = {
                    navController.navigate(route = "${Route.MemoEdit.name}/${uiState.lastPostedMind?.id}/${uiState.lastPostedMind?.memo?.id ?: -1}")
                }
            )
            else -> MindMemoCard(
                text = uiState.lastPostedMind?.memo?.text ?: "",
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}


@Composable
fun MindMemoCard(
    modifier: Modifier = Modifier,
    text: String,
    commentSize: Int = 0
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    var memoMaxLine by remember { mutableStateOf(1) }


    Column(
        modifier = modifier.then(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(RemindMaterialTheme.colorScheme.bg_muted)
                .fillMaxWidth()
                .padding(12.dp)
                .onSizeChanged {
                    scope.launch {
                        val height = with(density) { it.height.toDp() }
                        val textHeight = with(density) { 16.sp.toDp() }
                        val line = ((height - if (commentSize > 0) 21.dp else 0.dp) / textHeight).toInt()
                        if (line > 0) memoMaxLine = line
                    }
                }
        ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = RemindMaterialTheme.typography.regular_md,
            color = RemindMaterialTheme.colorScheme.fg_muted,
            overflow = TextOverflow.Ellipsis,
            maxLines = memoMaxLine
        )

        if (commentSize > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 21.dp)
            ) {
                Text(
                    text = "$commentSize",
                    style = RemindMaterialTheme.typography.regular_lg,
                    color = RemindMaterialTheme.colorScheme.fg_muted,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = stringResource(R.string.comment),
                    tint = RemindMaterialTheme.colorScheme.fg_subtle,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun addOnPostMindResult(
    navController: NavController,
    onResult: () -> Unit,
) {
    val postMindResult = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<Boolean>(POST_MIND_RESULT_KEY, false)
        ?.collectAsState()

    postMindResult?.value?.let { posted ->
        if(posted) {
            onResult()
        }
    }
}

@Composable
private fun EmptyPostMindCard(
    userDisplayName: String,
    onClickAddButton: () -> Unit
) {
    BackgroundContainer {
        Column(
            modifier = Modifier.padding(vertical = 68.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.home_empty_mind_box_message, userDisplayName),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = RemindMaterialTheme.colorScheme.fg_subtle, shape = CircleShape),
                onClick = onClickAddButton
            ) {
                Image(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
            }
        }
    }
}

@Composable
private fun EmptyPostMindLabelCard() {
    BackgroundContainer(modifier = Modifier.padding(vertical = 32.dp)) {
        Text(text = stringResource(id = R.string.home_empty_mind_history), style = RemindMaterialTheme.typography.regular_lg)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmptyMemoCard(
    onClickAddButton: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(size = 20.dp))
            .background(RemindMaterialTheme.colorScheme.bg_muted)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = stringResource(id = R.string.home_empty_mind_memo), style = RemindMaterialTheme.typography.regular_lg)

        if (onClickAddButton != null) {
            Spacer(modifier = Modifier.height(12.dp))

            CompositionLocalProvider(
                LocalMinimumInteractiveComponentEnforcement provides false
            ) {
                IconButton(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = RemindMaterialTheme.colorScheme.fg_subtle),
                    onClick = onClickAddButton
                ) {
                    Image(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
                }
            }

        }
    }
}
