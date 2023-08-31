package com.ccc.remind.presentation.ui.component.pageComponent.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun UserPictureEditButton(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    onClick: () -> Unit
) {
    Box(modifier) {
        FilledIconButton(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier.size(100.dp)
        ) {
            Icon(
                painter = painter ?: painterResource(id = R.drawable.ic_user),
                contentDescription = stringResource(id = R.string.user_icon),
                tint = RemindMaterialTheme.colorScheme.bg_muted,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(RemindMaterialTheme.colorScheme.fg_subtle)
                    .padding(16.dp)
            )
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