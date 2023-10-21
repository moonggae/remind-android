package com.ccc.remind.presentation.ui.mindPost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.icon.RoundedTextIcon
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.ImageDialog
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.ImageUploadBar
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.MindMemoTextField
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.StepBar
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.Constants.POST_MIND_RESULT_KEY

private const val TAG = "MindPostEditScreen"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindPostEditScreen(
    navController: NavController,
    viewModel: MindPostViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val sharedUiState by sharedViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var maxSize by remember { mutableStateOf(IntSize.Zero) }
    val photoItemWidth = with(LocalDensity.current) { (maxSize.width.toDp() - 20.dp).div(other = 3) }

    val TAG = "로그"

    val nextButton: @Composable (() -> Unit) = {
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentEnforcement provides false
        ) {
            TextButton(
                onClick = {
                    viewModel.submitMind {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(POST_MIND_RESULT_KEY, true)
                        navController.navigate(Route.MindPost.Complete.name)
                    }
                },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.to_complete),
                    style = RemindMaterialTheme.typography.regular_xl,
                    color = RemindMaterialTheme.colorScheme.text_button_blue
                )
            }
        }
    }

    BasicScreen(
        appBar = {
            AppBar(
                title = "",
                navController = navController,
                suffix = nextButton
            )
        },
        modifier = Modifier.onSizeChanged { maxSize = it }
    ) {
        Text(
            text = "${stringResource(R.string.step)} 2 / 2",
            style = RemindMaterialTheme.typography.bold_md
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.mind_post_edit_title, sharedUiState.user?.displayName ?: "유저"),
            style = RemindMaterialTheme.typography.bold_xl
        )

        Spacer(modifier = Modifier.height(12.dp))

        StepBar(maxStep = 2, currentStep = 1)

        Spacer(modifier = Modifier.height(34.dp))

        var selectedImage by remember {
            mutableStateOf<ImageFile?>(null)
        }

        if (selectedImage != null) {
            ImageDialog(
                images = uiState.uploadedPhotos,
                initialIndex = uiState.uploadedPhotos.indexOf(selectedImage),
                onDismissRequest = { selectedImage = null }
            )
        }

        ImageUploadBar(
            onUploadPhoto = viewModel::uploadPhotos,
            onDeletePhoto = viewModel::deleteUploadedPhoto,
            uploadedPhotos = uiState.uploadedPhotos,
            onClickUploadedImage = { selectedImage = it },
            itemWidth = photoItemWidth
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = stringResource(R.string.mind_post_edit_label_main_mind),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_muted
        )
        Text(
            text = stringResource(R.string.mind_post_edit_label_sub_main_mind),
            style = RemindMaterialTheme.typography.regular_md,
            color = RemindMaterialTheme.colorScheme.fg_muted
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.selectedMindCards.isNotEmpty())
            RoundedTextIcon(text = uiState.selectedMindCards.first().displayName)

        Spacer(modifier = Modifier.height(28.dp))

        if (uiState.selectedMindCards.size > 1)
            Column {
                Text(
                    text = stringResource(R.string.mind_post_edit_label_sub_mind),
                    style = RemindMaterialTheme.typography.bold_lg,
                    color = RemindMaterialTheme.colorScheme.fg_muted
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ) {
                    items(uiState.selectedMindCards.size - 1) { index ->
                        RoundedTextIcon(text = uiState.selectedMindCards[index + 1].displayName)
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
            }


        Text(
            text = stringResource(R.string.mind_post_edit_label_memo),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_muted
        )

        Spacer(modifier = Modifier.height(12.dp))

        MindMemoTextField(
            value = uiState.memo ?: "",
            onValueChange = viewModel::updateMemo
        )
    }
}