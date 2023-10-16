package com.ccc.remind.presentation.ui.component.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.transform.CircleCropTransformation
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest

@Composable
fun UserProfileIcon(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    size: Dp = 44.dp
) {
    if(imageUrl == null) {
        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = stringResource(R.string.user_icon),
            tint = RemindMaterialTheme.colorScheme.bg_muted,
            modifier = modifier.then(
                Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(RemindMaterialTheme.colorScheme.fg_subtle)
                    .padding(8.dp)
            )
        )
    } else {
        AsyncImage(
            model = buildCoilRequest(LocalContext.current, imageUrl, listOf(CircleCropTransformation())),
            contentDescription = stringResource(R.string.user_profile_image),
            contentScale = ContentScale.Crop,
            modifier = modifier.then(
                Modifier.size(size)
            )
        )
    }
}