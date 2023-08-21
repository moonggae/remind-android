package com.ccc.remind.presentation.ui.component.memo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.toFormatString
import java.time.ZonedDateTime

@Composable
fun MyComment(
    modifier: Modifier = Modifier,
    text: String,
    isLiked: Boolean,
    createdAt: ZonedDateTime
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier.then(
            Modifier.fillMaxWidth()
        )
    ) {
        Text(
            text = createdAt.toFormatString("a h:mm"),
            style = RemindMaterialTheme.typography.regular_sm,
            color = RemindMaterialTheme.colorScheme.fg_default,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box {
            Box(
                modifier = Modifier.padding(
                    start = 4.dp,
                    bottom = 4.dp
                )
            ) {
                Text(
                    text = text,
                    color = RemindMaterialTheme.colorScheme.bg_default,
                    style = RemindMaterialTheme.typography.regular_md,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = 20.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(RemindMaterialTheme.colorScheme.accent_default)
                        .padding(
                            horizontal = 20.dp,
                            vertical = 9.dp
                        )
                )
            }

            if (isLiked) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart_fill_circle),
                    contentDescription = stringResource(R.string.heart),
                    modifier = Modifier
                        .align(alignment = Alignment.BottomStart)
                        .size(20.dp)
                )
            }
        }
    }
}