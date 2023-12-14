package com.ccc.remind.presentation.ui.component.pageComponent.memo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.icon.UserProfileIcon
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.toFormatString
import java.time.ZonedDateTime

@Composable
fun OtherComment(
    modifier: Modifier = Modifier,
    text: String,
    isLiked: Boolean,
    createdAt: ZonedDateTime,
    profileImageUrl: String? = null,
    onClickLike: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier.then(
            Modifier.fillMaxWidth()
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.weight(1f)
        ) {
            UserProfileIcon(
                imageUrl = profileImageUrl,
                size = 32.dp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text,
                color = RemindMaterialTheme.colorScheme.fg_default,
                style = RemindMaterialTheme.typography.regular_md,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 20.dp
                        )
                    )
                    .background(RemindMaterialTheme.colorScheme.bg_subtle)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 9.dp
                    )
                    .weight(1f, false)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = createdAt.toFormatString("a h:mm"),
                style = RemindMaterialTheme.typography.regular_sm,
                color = RemindMaterialTheme.colorScheme.fg_default,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        IconButton(
            onClick = onClickLike,
            modifier = Modifier.size(32.dp)
        ) {
            if (isLiked) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart_fill),
                    contentDescription = stringResource(R.string.heart_fill),
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = stringResource(R.string.heart_empty),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}