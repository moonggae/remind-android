package com.ccc.remind.presentation.ui.mindPost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.mind.MindMemo
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.mind.MindPostCard
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.MenuButton
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.button.ShareButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.dialog.AlertDialogManager
import com.ccc.remind.presentation.ui.component.dialog.DefaultAlertDialog
import com.ccc.remind.presentation.ui.component.dialog.MenuBottomSheetManager
import com.ccc.remind.presentation.ui.component.icon.RoundedTextIcon
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.model.ButtonModel
import com.ccc.remind.presentation.ui.component.model.ButtonPriority
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.ImageDialog
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.ImageListBar
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.MindMemoTextField
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.ViewCardsDetailTextButton
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserProfileCard
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserRelation
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest
import com.ccc.remind.presentation.util.toFormatString
import kotlinx.coroutines.launch

// TODO 코드 정리

@Composable
fun MindPostDetailScreen(
    navController: NavController,
    viewModel: MindPostViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sharedUiState by sharedViewModel.uiState.collectAsStateWithLifecycle()
    val isMyPost = sharedUiState.currentUser?.uuid == uiState.openedPost?.user?.id
    var maxSize by remember { mutableStateOf(IntSize.Zero) }
    val photoItemWidth = with(LocalDensity.current) { (maxSize.width.toDp() - 60.dp).div(other = 3) }
    var selectedImage by remember {
        mutableStateOf<ImageFile?>(null)
    }

    val scope = rememberCoroutineScope()

    val deleteAlertDialog = remember { AlertDialogManager(scope) }
    val menu = remember { MenuBottomSheetManager(scope) }
    initMenu(menu, uiState, navController, viewModel, deleteAlertDialog)
    initDeleteAlertDialog(deleteAlertDialog, menu, viewModel, uiState, navController)

    BasicScreen(
        appBar = {
            AppBar(
                title = "",
                navController = navController,
                suffix = {
                    ShareButton { /* TODO */ }
                    if (isMyPost) {
                        MenuButton { menu.open() }
                    }
                }
            )
        },
        modifier = Modifier
            .onSizeChanged { maxSize = it }
            .verticalScroll(rememberScrollState()),
        padding = PaddingValues(start = 20.dp, end = 20.dp, bottom = (56 + 38 + 12).dp)
    ) {
        uiState.openedPost?.let { post ->
            TitleView(
                viewType = uiState.viewType,
                post = post,
                navController = navController,
                isMine = isMyPost
            )

            Spacer(modifier = Modifier.height(27.dp))

            MindCardView(
                url = post.cards.first().card.imageUrl ?: "",
                modifier = Modifier
                    .width(153.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(28.dp))

            MindListView(navController, post.cards)

            post.memo?.let { memo ->
                MemoView(memo)
            }

            if (post.images.isNotEmpty()) {
                PhotoView(
                    post = post,
                    photoItemWidth = photoItemWidth,
                    onClickImage = { selectedImage = it }
                )
            }
        }
    }

    if (uiState.viewType == PostViewType.FIRST_POST) {
        ConfirmButton(navController)
    }

    selectedImage?.let {
        uiState.openedPost?.let { post ->
            ImageDialog(
                images = post.images,
                initialIndex = post.images.indexOf(it),
                onDismissRequest = { selectedImage = null }
            )
        }
    }

    if (isMyPost) {
        menu.instance()
        deleteAlertDialog.instance()
    }
}

@Composable
private fun initDeleteAlertDialog(
    deleteAlertDialog: AlertDialogManager,
    menu: MenuBottomSheetManager,
    viewModel: MindPostViewModel,
    uiState: MindPostUiState,
    navController: NavController
) {
    deleteAlertDialog.init(
        useDefaultCancelButton = true,
        contentResId = R.string.mind_post_complete_alert_delete,
        buttons = { manager ->
            listOf(ButtonModel(
                textResId = R.string.to_delete,
                priority = ButtonPriority.WARN,
                onClick = {
                    manager.close()
                    menu.close()
                    viewModel.deleteMind {
                        when (uiState.viewType) {
                            PostViewType.FIRST_POST -> {
                                navController.popBackStack(
                                    route = Route.Main.Home.name,
                                    saveState = false,
                                    inclusive = false
                                )
                            }

                            PostViewType.DETAIL -> {
                                navController.popBackStack(
                                    route = Route.Main.MindHistory.name,
                                    saveState = false,
                                    inclusive = false
                                )
                            }
                        }
                    }
                }
            ))
        }
    )
}

@Composable
private fun initMenu(
    menu: MenuBottomSheetManager,
    uiState: MindPostUiState,
    navController: NavController,
    viewModel: MindPostViewModel,
    deleteAlertDialog: AlertDialogManager
) {
    menu.init(
        buttons = listOf(
            ButtonModel(
                textResId = R.string.to_update,
                priority = ButtonPriority.DEFAULT,
                onClick = {
                    when (uiState.viewType) {
                        PostViewType.FIRST_POST -> {
                            navController.popBackStack(
                                Route.MindPost.SelectCard.name,
                                saveState = true,
                                inclusive = false
                            )
                        }

                        PostViewType.DETAIL -> {
                            uiState.openedPost?.let { post ->
                                viewModel.initEditPost(post)
                                navController.navigate("${Route.MindPost.SelectCard.name}?type=${PostViewType.DETAIL}")
                            }
                        }
                    }
                }
            ),
            ButtonModel(
                textResId = R.string.to_delete,
                priority = ButtonPriority.WARN,
                onClick = { deleteAlertDialog.open() },
            )
        ),
        useDefaultCancelButton = true
    )
}

@Composable
private fun TitleView(
    viewType: PostViewType,
    post: MindPost,
    navController: NavController,
    isMine: Boolean
) {
    if (viewType == PostViewType.FIRST_POST) {
        Text(
            text = stringResource(R.string.mind_post_complete_title, post.user?.displayName ?: ""),
            style = RemindMaterialTheme.typography.bold_xl,
            color = RemindMaterialTheme.colorScheme.fg_default
        )
    } else {
        Text(
            text = post.createdAt.toFormatString("yyyy년 M월 d일"),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_default
        )

        Spacer(modifier = Modifier.height(16.dp))

        post.user?.let { user ->
            UserProfileCard(
                user = user,
                showTextSuffix = false,
                navController = navController,
                relation = if (isMine) UserRelation.ME else UserRelation.FRIEND
            )
        }

    }
}

@Composable
private fun ConfirmButton(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.to_confirm),
            onClick = {
                navController.popBackStack(
                    route = Route.Main.Home.name,
                    inclusive = false,
                    saveState = false
                )
            }
        )

        Spacer(modifier = Modifier.height(38.dp))
    }
}

@Composable
private fun PhotoView(
    post: MindPost,
    photoItemWidth: Dp,
    onClickImage: (ImageFile) -> Unit = {}
) {
    Spacer(modifier = Modifier.height(38.dp))

    Text(
        text = stringResource(R.string.mind_post_complete_label_photo),
        style = RemindMaterialTheme.typography.bold_lg,
        color = RemindMaterialTheme.colorScheme.fg_muted
    )

    Spacer(modifier = Modifier.height(12.dp))

    ImageListBar(
        photos = post.images,
        itemWidth = photoItemWidth,
        onClickImage = onClickImage
    )
}

@Composable
private fun MemoView(memo: MindMemo) {
    Spacer(modifier = Modifier.height(38.dp))

    Text(
        text = stringResource(R.string.mind_post_complete_label_memo),
        style = RemindMaterialTheme.typography.bold_lg,
        color = RemindMaterialTheme.colorScheme.fg_muted
    )

    Spacer(modifier = Modifier.height(12.dp))

    MindMemoTextField(
        value = memo.text,
        enabled = false,
        modifier = Modifier.defaultMinSize(minHeight = 34.dp)
    )
}

@Composable
private fun MindListView(navController: NavController, cards: List<MindPostCard>) {
    MindListLabel(navController, cards.map { it.card.id })
    Spacer(modifier = Modifier.height(4.dp))
    MindListContent(cards.map { it.card.displayName })
}

@Composable
private fun MindListContent(cardNames: List<String>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
        items(cardNames.size) { index ->
            RoundedTextIcon(text = cardNames[index])
        }
    }
}

@Composable
private fun MindListLabel(navController: NavController, cardIds: List<Int>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.mind_post_complete_label_posted_minds),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_muted
        )

        ViewCardsDetailTextButton(
            navController = navController,
            cardIds = cardIds
        )
    }
}

@Composable
private fun MindCardView(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = buildCoilRequest(
            context = LocalContext.current,
            url = url
        ),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
fun DeleteMindAlertDialog(
    openAlertDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val scope = rememberCoroutineScope()

    if (openAlertDialog) {
        DefaultAlertDialog(
            contentText = stringResource(R.string.mind_post_complete_alert_delete),
            cancelLabelText = stringResource(R.string.to_cancel),
            confirmLabelText = stringResource(R.string.to_delete),
            onClickConfirmButton = { scope.launch { onConfirm.invoke() } },
            onClickCancelButton = { scope.launch { onDismiss.invoke() } },
            onDismissRequest = { scope.launch { onDismiss.invoke() } }
        )
    }
}