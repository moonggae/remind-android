package com.ccc.remind.presentation.ui.setting

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.dialog.AlertDialogManager
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.model.ButtonModel
import com.ccc.remind.presentation.ui.component.model.ButtonPriority
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
@Composable
fun SettingScreen(
    navController: NavController,
    notificationViewModel: NotificationSettingViewModel
) {
    BasicScreen(
        viewModel = notificationViewModel,
        appBar = {
            AppBar(
                title = stringResource(R.string.setting_appbar_title),
                navController = navController
            )
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.setting_label_notification),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            NotificationPermissionSwitch(notificationViewModel)
        }
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun NotificationPermissionSwitch(
    viewModel: NotificationSettingViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isOnNotification = viewModel.isOnNotification.collectAsState().value

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = viewModel::toggleNotification,
    )

    val notificationPermissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    val notificationSettingDialogManager = remember {
        AlertDialogManager(scope).init(
            contentResId = R.string.setting_notification_move_to_setting,
            useDefaultCancelButton = true,
            buttons = {
                listOf(
                    ButtonModel(
                        textResId = R.string.to_move,
                        priority = ButtonPriority.ACCENT,
                        onClick = {
                            scope.launch {
                                viewModel.openAppSettings(context)
                                it.close()
                            }
                        }
                    )
                )
            },
        )
    }


    Switch(
        colors = SwitchDefaults.colors(
            checkedBorderColor = RemindMaterialTheme.colorScheme.accent_default,
            checkedTrackColor = RemindMaterialTheme.colorScheme.accent_default,
            checkedThumbColor = RemindMaterialTheme.colorScheme.bg_default,
            uncheckedBorderColor = RemindMaterialTheme.colorScheme.fg_subtle,
            uncheckedTrackColor = RemindMaterialTheme.colorScheme.fg_subtle,
            uncheckedThumbColor = RemindMaterialTheme.colorScheme.bg_default
        ),
        checked = isOnNotification,
        onCheckedChange = { isChecked ->
            if (isChecked) {
                if (viewModel.isRuntimePermissionRequired()) {
                    when {
                        notificationPermissionState.status.isGranted -> {
                            viewModel.toggleNotification(true)
                        }

                        notificationPermissionState.status.shouldShowRationale -> {
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }

                        else -> {
                            notificationSettingDialogManager.open()
                            viewModel.toggleNotification(true)
                        }
                    }
                } else {
                    if (!viewModel.areNotificationsEnabled(context)) {
                        notificationSettingDialogManager.open()
                    } else {
                        viewModel.toggleNotification(true)
                    }
                }
            } else {
                viewModel.toggleNotification(false)
            }
        }
    )

    notificationSettingDialogManager.instance()
}