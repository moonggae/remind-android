package com.ccc.remind.presentation.ui.user.userProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.presentation.ui.component.text.SecondaryText
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest

@Composable
fun UserProfileView(
    modifier: Modifier = Modifier,
    user: User?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if(user == null) {
            SecondaryText(
                text = "사용자 정보를 불러올 수 없어요.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 120.dp)
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))

            if(user.profileImage == null) {
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
                    model = buildCoilRequest(
                        LocalContext.current,
                        user.profileImage!!.url,
                        listOf(CircleCropTransformation())
                    ),
                    contentDescription = stringResource(R.string.user_profile_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = user.displayName ?: "",
                style = RemindMaterialTheme.typography.bold_xxl,
                color = RemindMaterialTheme.colorScheme.fg_default
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}