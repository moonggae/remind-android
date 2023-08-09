package com.ccc.remind.presentation.ui.component.mindPost

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ccc.remind.domain.entity.mind.ImageFile

@Composable
fun ImageUploadBar(
    modifier: Modifier = Modifier,
    onUploadPhoto: (List<Uri>) -> Unit,
    onDeletePhoto: (ImageFile) -> Unit,
    uploadedPhotos: (List<ImageFile>),
    onClickUploadedImage: (ImageFile) -> Unit = {},
    itemWidth: Dp,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        modifier = modifier
    ) {
        item {
            UploadPhotoButton(
                onResult = onUploadPhoto,
                maxItems = 8
            ) {
                UploadPhotoCard(
                    currentCount = uploadedPhotos.size,
                    maxSize = 8,
                    modifier = Modifier
                        .width(itemWidth)
                        .aspectRatio(1f)
                )
            }
        }

        if (uploadedPhotos.isEmpty()) return@LazyRow

        items(count = uploadedPhotos.size) { index ->
            UploadedPhotoCard(
                item = uploadedPhotos[index],
                onDelete = onDeletePhoto,
                modifier = Modifier
                    .width(itemWidth)
                    .aspectRatio(1f)
                    .clickable {
                        onClickUploadedImage(uploadedPhotos[index])
                    }
            )
        }
    }
}