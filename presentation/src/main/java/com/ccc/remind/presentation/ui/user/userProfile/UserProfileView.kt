package com.ccc.remind.presentation.ui.user.userProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import coil.transform.CircleCropTransformation
import com.ccc.remind.R
import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest

@Composable
fun UserProfileView(userProfile: UserProfile) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))

        if(userProfile.profileImage == null) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = stringResource(R.string.user_icon),
                tint = RemindMaterialTheme.colorScheme.bg_muted,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(RemindMaterialTheme.colorScheme.fg_subtle)
            )
        } else {
            AsyncImage(
                model = buildCoilRequest(LocalContext.current, userProfile.profileImage!!.url, listOf(CircleCropTransformation())),
                contentDescription = stringResource(R.string.user_profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = userProfile.displayName ?: "",
            style = RemindMaterialTheme.typography.bold_xxl,
            color = RemindMaterialTheme.colorScheme.fg_default
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}