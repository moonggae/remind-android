package com.ccc.remind.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
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
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.mind.MindPostCard
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.OutlinedTextButton
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BackgroundContainer
import com.ccc.remind.presentation.ui.component.dialog.AlertDialog
import com.ccc.remind.presentation.ui.component.icon.CircleIndicator
import com.ccc.remind.presentation.ui.component.icon.RoundedTextIcon
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.ViewDetailTextButton
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.notification.NotificationViewModel
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.Constants.POST_MIND_RESULT_KEY
import com.ccc.remind.presentation.util.buildCoilRequest
import kotlinx.coroutines.launch

/* TODO
- notification list page
- request notification permission
*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val notificationUiState by notificationViewModel.uiState.collectAsState()
    val sharedUiState by sharedViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0
    )

    val titleText: String =
        if(pagerState.currentPage == 0)
            stringResource(id = R.string.home_title_greeting, sharedUiState.user?.displayName ?: "유저")
        else {
            if(sharedUiState.friend == null)
                "누구와\n함께할까요?"
            else
                "${sharedUiState.friend!!.displayName}님과\n함께하고있어요"
        }

    Column(
        modifier = Modifier.padding(vertical = 28.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                titleText,
                style = RemindMaterialTheme.typography.bold_xxl,
                modifier = Modifier.padding(top = 40.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                Box(modifier = Modifier.size(40.dp)) {
                    IconButton(onClick = {
                        scope.launch {
                            navController.navigate(Route.NotificationList.name)
                        }
                    }, modifier = Modifier.size(40.dp)) {
                        Image(
                            painter = painterResource(R.drawable.ic_notification),
                            contentDescription = null,
                            modifier = Modifier.background(Color.Transparent)
                        )
                    }

                    if (notificationUiState.isNotificationAlarmOn) {
                        Box(
                            Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(RemindMaterialTheme.colorScheme.alarm)
                                .align(TopEnd)
                        )
                    }
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

        CircleIndicator(
            size = 2,
            currentIndex = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
        
        HorizontalPager(
            pageCount = 2,
            state = pagerState
        ) { pageIndex ->
            when(pageIndex) {
                0 -> MyHomeContents(
                    postMind = uiState.post,
                    displayName = sharedUiState.user?.displayName,
                    navController = navController,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                1 -> OtherHomeContents(
                    postMind = uiState.friendPost,
                    displayName = sharedUiState.friend?.displayName,
                    navController = navController,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    onRequestFriendMind = viewModel::submitRequestFriendMind
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherHomeContents(
    modifier: Modifier = Modifier,
    postMind: MindPost?,
    displayName: String?,
    navController: NavController,
    onRequestFriendMind: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var openAskMindAlertDialog by remember { mutableStateOf(false) }
    val openDialog: () -> Unit = {
        scope.launch {
            openAskMindAlertDialog = true
        }
    }
    val closeDialog: () -> Unit = {
        scope.launch {
            openAskMindAlertDialog = false
        }
    }


    Column(modifier) {
        if (postMind == null) {
            EmptyOtherPostMindCard(userDisplayName = displayName) {
                navController.navigate(Route.Invite.name)
            }
        } else {
            PostMindCard(
                mindCardUrl = postMind.cards.first().card.imageUrl ?: "",
                modifier = Modifier.align(CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(18.dp))

            PrimaryButton(
                text = stringResource(R.string.home_ask_mind_button),
                textStyle = RemindMaterialTheme.typography.bold_lg,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .height(46.dp),
                onClick = openDialog
            )
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

            if(postMind?.cards?.isNotEmpty() == true) {
                ViewDetailTextButton {
                    // todo
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (postMind == null) {
            EmptyPostMindLabelCard()
        } else {
            PostMindLabelBar(cards = postMind.cards)
        }

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (postMind?.memo == null) 8.dp else 0.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_label_mind_memo),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted,
            )

            if(postMind?.memo != null) {
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(route = "${Route.MemoEdit.name}/${postMind.id}/${postMind.memo?.id ?: -1}/true")
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_light),
                            contentDescription = stringResource(id = R.string.arrow_light_icon),
                            tint = Color(0xFF686868),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }


        when (postMind?.memo) {
            null -> EmptyMemoCard()
            else -> MindMemoCard(
                text = postMind.memo?.text ?: "",
                modifier = Modifier.fillMaxHeight(),
                commentSize = postMind.memo?.comments?.size ?: 0
            )
        }
    }

    if(openAskMindAlertDialog) {
        AlertDialog(
            contentText = stringResource(R.string.home_ask_mind_dailog_content_text, displayName ?: ""),
            onClickConfirmButton = {
                onRequestFriendMind()
                closeDialog()
            },
            onClickCancelButton = closeDialog,
            onDismissRequest = closeDialog,
            buttonReverse = true,
            confirmLabelColor = RemindMaterialTheme.colorScheme.accent_default
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyHomeContents(
    modifier: Modifier = Modifier,
    postMind: MindPost?,
    displayName: String?,
    navController: NavController,
) {
    Column(modifier) {
        if (postMind == null) {
            EmptyPostMindCard(
                userDisplayName = displayName ?: "유저",
                onClickAddButton = { navController.navigate(Route.MindPost.CardList.name) }
            )
        } else {
            PostMindCard(
                mindCardUrl = postMind.cards.first().card.imageUrl ?: "",
                modifier = Modifier.align(CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(18.dp))

            PrimaryButton(
                text = "감정 기록하기",
                textStyle = RemindMaterialTheme.typography.bold_lg,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .height(46.dp),
                onClick = { navController.navigate(Route.MindPost.CardList.name) }
            )
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

            if(postMind?.cards?.isNotEmpty() == true) {
                ViewDetailTextButton {
                    // todo
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (postMind == null) {
            EmptyPostMindLabelCard()
        } else {
            PostMindLabelBar(cards = postMind.cards)
        }

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (postMind?.memo == null) 8.dp else 0.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_label_mind_memo),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            if(postMind?.memo != null) {
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(route = "${Route.MemoEdit.name}/${postMind?.id}/${postMind?.memo?.id ?: -1}/false")
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_light),
                            contentDescription = stringResource(id = R.string.arrow_light_icon),
                            tint = Color(0xFF686868),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }


        when {
            postMind == null -> EmptyMemoCard()
            postMind.memo == null -> EmptyMemoCard {
                navController.navigate(route = "${Route.MemoEdit.name}/${postMind.id}/${-1}/false")
            }

            else -> MindMemoCard(
                text = postMind.memo?.text ?: "",
                modifier = Modifier.fillMaxHeight(),
                commentSize = postMind.memo?.comments?.size ?: 0
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
    onResult: (Boolean) -> Unit,
) {
    val postMindResult = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<Boolean>(POST_MIND_RESULT_KEY, false)
        ?.collectAsState()

    postMindResult?.value?.let { posted ->
        onResult(posted)
    }
}

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

@Composable
fun PostMindCard(
    modifier: Modifier = Modifier,
    mindCardUrl: String,
) {
    AsyncImage(
        model = buildCoilRequest(
            context = LocalContext.current,
            url = mindCardUrl
        ),
        contentDescription = "",
        modifier = modifier.then(Modifier.width(153.dp)),
        contentScale = ContentScale.FillWidth,
    )
}


@Composable
fun EmptyOtherPostMindCard(
    userDisplayName: String?,
    onClickAddButton: () -> Unit
) {
    val text: String =
        if(userDisplayName == null)
            "감정을 공유하고 싶은\n상대가 있나요?"
        else
            stringResource(id = R.string.home_empty_other_mind_box_message, userDisplayName)



    BackgroundContainer {
        Column(
            modifier = Modifier.padding(vertical = 68.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            if(userDisplayName == null) {
                OutlinedTextButton(
                    text = "초대하기",
                    modifier = Modifier.height(30.dp),
                    onClick = onClickAddButton
                )
            }
        }
    }
}

@Composable
fun EmptyPostMindCard(
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
