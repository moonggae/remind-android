package com.ccc.remind.presentation.ui.user

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.MenuButton
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.dialog.AlertDialogManager
import com.ccc.remind.presentation.ui.component.dialog.MenuBottomSheetManager
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.layout.BackgroundedTextField
import com.ccc.remind.presentation.ui.component.model.ButtonModel
import com.ccc.remind.presentation.ui.component.model.ButtonPriority
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserPictureEditButton
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import kotlinx.coroutines.launch

@Composable
fun UserProfileEditScreen(
    navController: NavController = rememberNavController(),
    viewModel: UserProfileEditViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    val pickMediaLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = viewModel::updateProfileImage
        )

    val menu = remember { MenuBottomSheetManager(scope) }
    val deleteAccountAlert = remember { AlertDialogManager(scope) }
    initMenu(menu, deleteAccountAlert)
    initDeleteAccountAlert(deleteAccountAlert, menu, viewModel::submitDeleteAccount, sharedViewModel::logout)

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.user_profile_edit_appbar_title),
                navController = navController,
                suffix = {
                    MenuButton {
                        menu.open()
                    }
                }
            )
        }
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        UserPictureEditButton(
            profileImage = uiState.profileImage,
            modifier = Modifier.align(CenterHorizontally)
        ) {
            pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.user_profile_edit_label_display_name),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_muted
        )

        Spacer(modifier = Modifier.height(12.dp))

        BackgroundedTextField(
            value = uiState.displayName,
            onValueChange = viewModel::updateDisplayName
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.login_display_name_register_rule),
            style = RemindMaterialTheme.typography.regular_sm,
            color = RemindMaterialTheme.colorScheme.fg_muted
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = stringResource(id = R.string.to_save),
            enabled = uiState.isAnyEdited && uiState.isValidDisplayName
        ) {
            scope.launch {
                viewModel.submitUpdateUserProfile()
                sharedViewModel.initUser()
                navController.popBackStack()
            }
        }

        Spacer(modifier = Modifier.height(49.dp))

    }

    menu.instance()
    deleteAccountAlert.instance()
}

fun initMenu(
    menu: MenuBottomSheetManager,
    deleteAlertDialog: AlertDialogManager
) {
    menu.init(
        buttons = listOf(ButtonModel(
            textResId = R.string.user_profile_edit_menu_delete_account,
            priority = ButtonPriority.WARN,
            onClick = {
                deleteAlertDialog.open()
            }
        )),
        useDefaultCancelButton = true,
    )
}

@Composable
fun initDeleteAccountAlert(
    dialog: AlertDialogManager,
    menu: MenuBottomSheetManager,
    deleteAccount: () -> Unit,
    logout: () -> Unit
) {
    val scope = rememberCoroutineScope()

    dialog.init(
        buttons = { manager ->
            listOf(
                ButtonModel(
                    priority = ButtonPriority.WARN,
                    textResId = R.string.user_profile_edit_delete_account_alert_delete_button,
                    onClick = {
                        manager.close()
                        menu.close()
                        deleteAccount()
                        logout()
                    }
                )
            )
        },
        useDefaultCancelButton = true,
        contentResId = R.string.user_profile_edit_delete_account_alert_content,
        titleResId = R.string.user_profile_edit_delete_account_alert_title,
    )
}