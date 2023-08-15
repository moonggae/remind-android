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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.button.TextFillButton
import com.ccc.remind.presentation.ui.component.dialog.AlertDialog
import com.ccc.remind.presentation.ui.component.dialog.ModalBottomSheet
import com.ccc.remind.presentation.ui.component.icon.RoundedTextIcon
import com.ccc.remind.presentation.ui.component.mindPost.ImageDialog
import com.ccc.remind.presentation.ui.component.mindPost.ImageListBar
import com.ccc.remind.presentation.ui.component.mindPost.MindMemoTextField
import com.ccc.remind.presentation.ui.component.mindPost.ViewDetailTextButton
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindPostCompleteScreen(
    navController: NavController,
    viewModel: MindPostViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val sharedUiState by sharedViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var maxSize by remember { mutableStateOf(IntSize.Zero) }
    val photoItemWidth = with(LocalDensity.current) { (maxSize.width.toDp() - 20.dp).div(other = 3) }
    var selectedImage by remember {
        mutableStateOf<ImageFile?>(null)
    }

    val scope = rememberCoroutineScope()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var openAlertDialog by rememberSaveable { mutableStateOf(false) }


    if (selectedImage != null) {
        ImageDialog(
            images = uiState.uploadedPhotos,
            initialIndex = uiState.uploadedPhotos.indexOf(selectedImage),
            onDismissRequest = { selectedImage = null }
        )
    }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = (56 + 38 + 12).dp)
            .onSizeChanged { maxSize = it }
            .verticalScroll(scrollState)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { navController.popBackStack() }) {
                Icon(
                    painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = stringResource(R.string.back)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        // todo
                    }) {
                    Icon(
                        painterResource(id = R.drawable.ic_share),
                        contentDescription = stringResource(R.string.share)
                    )
                }

                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        scope.launch {
                            openBottomSheet = true
                        }
                    }) {
                    Icon(
                        painterResource(id = R.drawable.ic_meatball),
                        contentDescription = stringResource(R.string.menu)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(41.dp))

        Text(
            text = stringResource(R.string.mind_post_complete_title, sharedUiState.user?.displayName ?: "유저"),
            style = RemindMaterialTheme.typography.bold_xl
        )

        Spacer(modifier = Modifier.height(27.dp))

        AsyncImage(
            model = buildCoilRequest(
                context = LocalContext.current,
                url = uiState.postedMind?.cards?.first()?.card?.imageUrl ?: ""
            ),
            contentDescription = "",
            modifier = Modifier
                .width(153.dp)
                .align(CenterHorizontally),
            contentScale = ContentScale.FillWidth,
        )

        Spacer(modifier = Modifier.height(28.dp))

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

            ViewDetailTextButton {
                // todo
            }
        }


        Spacer(modifier = Modifier.height(4.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ) {
            items(uiState.selectedMindCards.size) { index ->
                RoundedTextIcon(text = uiState.selectedMindCards[index].displayName)
            }
        }

        if (uiState.memo?.isNotEmpty() == true) {
            Spacer(modifier = Modifier.height(38.dp))

            Text(
                text = stringResource(R.string.mind_post_complete_label_memo),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            Spacer(modifier = Modifier.height(12.dp))

            MindMemoTextField(
                value = uiState.memo ?: "",
                enabled = false,
                modifier = Modifier.defaultMinSize(minHeight = 34.dp)
            )
        }

        if (uiState.uploadedPhotos.isNotEmpty()) {
            Spacer(modifier = Modifier.height(38.dp))

            Text(
                text = stringResource(R.string.mind_post_complete_label_photo),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            Spacer(modifier = Modifier.height(12.dp))

            ImageListBar(
                photos = uiState.uploadedPhotos,
                itemWidth = photoItemWidth,
                onClickImage = { selectedImage = it }
            )
        }
    }


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


    if (openBottomSheet) {
        ModalBottomSheet( // todo: divider color check
            onDismissRequest = { openBottomSheet = false },
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
            padding = PaddingValues(
                bottom = 30.dp,
                start = 20.dp,
                end = 20.dp
            ) // todo : divider height 다름 현상
        ) {
            TextFillButton(
                text = stringResource(R.string.to_update),
                onClick = {
                    scope.launch {
                        openBottomSheet = false
                        navController.popBackStack(Route.MindPost.CardList.name, saveState = true, inclusive = false)
                    }
                }
            )

            Divider(
                thickness = 0.5.dp,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            TextFillButton(
                text = stringResource(R.string.to_delete),
                onClick = {
                    scope.launch {
                        openAlertDialog = true
                    }
                }
            )

            Divider(
                thickness = 0.5.dp,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            TextFillButton(
                text = stringResource(R.string.to_cancel),
                contentColor = RemindMaterialTheme.colorScheme.accent_default,
                onClick = {
                    scope.launch {
                        openBottomSheet = false
                    }
                }
            )
        }
    }


    if (openAlertDialog) {
        AlertDialog(
            contentText = stringResource(R.string.mind_post_complete_alert_delete),
            cancelLabelText = stringResource(R.string.to_cancel),
            confirmLabelText = stringResource(R.string.to_delete),
            onClickConfirmButton = {
                scope.launch {
                    openAlertDialog = false
                    openBottomSheet = false
                    viewModel.deleteMind {
                        navController.popBackStack(
                            route = Route.Main.Home.name,
                            saveState = false,
                            inclusive = false
                        )
                    }
                }
            },
            onClickCancelButton = { scope.launch { openAlertDialog = false } },
            onDismissRequest = { scope.launch { openAlertDialog = false } }
        )
    }
}

