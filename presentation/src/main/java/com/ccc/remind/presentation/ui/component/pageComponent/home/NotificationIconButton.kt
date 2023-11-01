package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun NotificationIconButton(
    isNotificationAlarmOn: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.size(40.dp)) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = null,
                modifier = Modifier.background(Color.Transparent)
            )
        }

        if (isNotificationAlarmOn) {
            Box(
                Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(RemindMaterialTheme.colorScheme.alarm)
                    .align(Alignment.TopEnd)
            )
        }
    }
}