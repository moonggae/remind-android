package com.ccc.remind.presentation.ui.mindPost

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.presentation.ui.component.mindPost.ImageDialog
import com.ccc.remind.presentation.ui.component.mindPost.StepBar
import com.ccc.remind.presentation.ui.component.mindPost.UploadPhotoButton
import com.ccc.remind.presentation.ui.component.mindPost.UploadPhotoCard
import com.ccc.remind.presentation.ui.component.mindPost.UploadedPhotoCard
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

private const val TAG = "MindPostEditScreen"


@Composable
fun MindPostEditScreen(
    navController: NavController,
    viewModel: MindPostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var maxSize by remember { mutableStateOf(IntSize.Zero) }
    val photoItemWidth = with(LocalDensity.current) { (maxSize.width.toDp() - 20.dp).div(other = 3) }

    Column(
        modifier = Modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 32.dp
            )
            .onSizeChanged {
                maxSize = it
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    painterResource(id = R.drawable.ic_x),
                    contentDescription = stringResource(R.string.exit)
                )
            }
        }
        Text(
            text = "${stringResource(R.string.step)} 2 / 2",
            style = RemindMaterialTheme.typography.bold_md
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "님의 감정을 알려주세요!",
            style = RemindMaterialTheme.typography.bold_xl
        )

        Spacer(modifier = Modifier.height(12.dp))

        StepBar(maxStep = 2, currentStep = 1)

        Spacer(modifier = Modifier.height(34.dp))

        var selectedImage by remember {
            mutableStateOf<ImageFile?>(null)
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
        ) {
            item {
                UploadPhotoButton(
                    onResult = viewModel::uploadPhotos,
                    maxItems = 8
                ) {
                    UploadPhotoCard(
                        currentCount = uiState.uploadedPhotos.size,
                        maxSize = 8,
                        modifier = Modifier
                            .width(photoItemWidth)
                            .aspectRatio(1f)
                    )
                }
            }

            items(count = uiState.uploadedPhotos.size) { index ->
                UploadedPhotoCard(
                    item = uiState.uploadedPhotos[index],
                    onDelete = viewModel::deleteUploadedPhoto,
                    modifier = Modifier
                        .width(photoItemWidth)
                        .aspectRatio(1f)
                        .clickable {
                            selectedImage = uiState.uploadedPhotos[index]
                        }
                )
            }
        }

        if (selectedImage != null) {
            ImageDialog(
                images = uiState.uploadedPhotos,
                initialIndex = uiState.uploadedPhotos.indexOf(selectedImage),
                onDismissRequest = { selectedImage = null }
            )
        }
    }
}