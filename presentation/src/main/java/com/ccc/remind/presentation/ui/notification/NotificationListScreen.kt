package com.ccc.remind.presentation.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.navigation.moveToNotificationRoute
import com.ccc.remind.presentation.ui.component.button.OutlinedTextButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.notification.NotificationListItem
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.toTimestamp
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun NotificationListItemPreview() {
    Column(
        Modifier.background(RemindMaterialTheme.colorScheme.bg_subtle)
    ) {
        NotificationListItem(
            modifier = Modifier.fillMaxWidth(),
            title = "감정 기록",
            text = "봄님이 기록하신 감정을 확인해보세요!",
            timestamp = "지금"
        )

        NotificationListItem(
            modifier = Modifier.fillMaxWidth(),
            title = "감정 기록",
            text = "봄님이 기록하신 감정을 확인해보세요!",
            timestamp = "지금"
        )

        NotificationListItem(
            modifier = Modifier.fillMaxWidth(),
            title = "감정 기록",
            text = "봄님이 기록하신 감정을 확인해보세요!",
            timestamp = "지금"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListScreen(
    navController: NavController,
    viewModel: NotificationViewModel
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.notifications) {
        viewModel.updateNotificationsReadAll()
    }

    BasicScreen(
        viewModel = viewModel,
        appBar = {
            AppBar(
                title = stringResource(R.string.notification_list_title),
                navController = navController
            )
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        padding = PaddingValues(0.dp)
    ) {
        LazyColumn {
            items(
                count = uiState.notifications.size
            ) { index ->
                val item = uiState.notifications[index]
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                moveToNotificationRoute(
                                    navController = navController,
                                    notificationType = item.type,
                                    notificationTargetId = item.targetId
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            RemindMaterialTheme.colorScheme.bg_subtle,
                            Color.Transparent, Color.Transparent, Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp),
                        shape = RectangleShape
                    ) {
                        NotificationListItem(
                            title = item.title ?: "",
                            text = item.body ?: "",
                            timestamp = item.createdAt.toTimestamp()
                        )
                    }
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                ) {
                    OutlinedTextButton(
                        text = stringResource(R.string.notification_list_load_before_items_button),
                        modifier = Modifier.height(30.dp),
                        onClick = viewModel::getBeforeNotifications
                    )
                }
            }
        }
    }
}