package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDialog(
    images: List<ImageFile>,
    initialIndex: Int,
    contentScale: ContentScale = ContentScale.Crop,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                pageCount = images.size,
                state = PagerState(
                    initialPage = initialIndex
                ),
            ) { index ->
                AsyncImage(
                    model = buildCoilRequest(
                        context = LocalContext.current,
                        url = images[index].url
                    ),
                    contentDescription = "",
                    contentScale = contentScale
                )
            }

            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_x),
                    contentDescription = stringResource(R.string.close_button),
                    tint = RemindMaterialTheme.colorScheme.button_disabled
                )
            }
        }
    }
}