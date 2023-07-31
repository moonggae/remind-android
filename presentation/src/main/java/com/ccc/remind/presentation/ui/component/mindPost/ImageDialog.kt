package com.ccc.remind.presentation.ui.component.mindPost

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.ImageFile

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDialog(
    images: List<ImageFile>,
    initialIndex: Int,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            HorizontalPager(
                pageCount = images.size,
                state = PagerState(
                    initialPage = initialIndex
                )
            ) { index ->
                val request = ImageRequest.Builder(LocalContext.current)
                    .data(images[index].url)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .build()


                AsyncImage(
                    model = request,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }

            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_x),
                    contentDescription = "Close"
                )
            }
        }
    }
}