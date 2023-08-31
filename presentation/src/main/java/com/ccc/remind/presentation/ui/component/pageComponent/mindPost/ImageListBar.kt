package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

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
fun ImageListBar(
    modifier: Modifier = Modifier,
    photos: List<ImageFile>,
    onClickImage: (ImageFile) -> Unit = {},
    itemWidth: Dp,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        modifier = modifier
    ) {
        if (photos.isEmpty()) return@LazyRow

        items(count = photos.size) { index ->
            UploadedPhotoCard(
                item = photos[index],
                modifier = Modifier
                    .width(itemWidth)
                    .aspectRatio(1f)
                    .clickable {
                        onClickImage(photos[index])
                    }
            )
        }
    }
}