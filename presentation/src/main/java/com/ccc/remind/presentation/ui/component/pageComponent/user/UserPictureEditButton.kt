package com.ccc.remind.presentation.ui.component.pageComponent.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest
import com.ccc.remind.presentation.util.extensions.domain.url

@Composable
fun UserPictureEditButton(
    modifier: Modifier = Modifier,
    profileImage: ImageFile? = null,
    onClick: () -> Unit
) {
    Box(modifier) {
        FilledIconButton(
            onClick = onClick,
            shape = CircleShape,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = RemindMaterialTheme.colorScheme.bg_default
            ),
            modifier = Modifier.size(100.dp)
        ) {
            if(profileImage == null) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = stringResource(id = R.string.user_icon),
                    tint = RemindMaterialTheme.colorScheme.bg_muted,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(RemindMaterialTheme.colorScheme.fg_subtle)
                        .padding(16.dp)
                )
            }
            else {
                AsyncImage(
                    model = buildCoilRequest(
                        LocalContext.current,
                        profileImage.url
                    ),
                    contentDescription = "user profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .background(RemindMaterialTheme.colorScheme.bg_default)
                        .fillMaxSize()
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = stringResource(id = R.string.camera),
            tint = RemindMaterialTheme.colorScheme.fg_subtle,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(RemindMaterialTheme.colorScheme.bg_subtle)
                .padding(4.dp)
                .align(Alignment.BottomEnd)
        )
    }
}