package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.domain.url

@Composable
fun UploadedPhotoCard(
    item: ImageFile,
    modifier: Modifier = Modifier,
    onDelete: ((ImageFile) -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = RemindMaterialTheme.colorScheme.fg_subtle,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .then(modifier)
    ) {
        val request = ImageRequest.Builder(LocalContext.current)
            .data(item.url)
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

        if (onDelete != null)
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
                color = Color.Transparent
            ) {
                IconButton(
                    onClick = { onDelete?.invoke(item) },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = RemindMaterialTheme.colorScheme.fg_subtle,
                    ),
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_x),
                        contentDescription = "Close"
                    )
                }
            }
    }
}