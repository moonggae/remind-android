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
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.layout.BackgroundedTextField
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

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.user_profile_edit_appbar_title),
                navController = navController
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

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = stringResource(id = R.string.to_save),
            enabled = uiState.isAnyEdited
        ) {
            scope.launch {
                viewModel.submitUpdateUserProfile()
                sharedViewModel.refreshUser()
                navController.popBackStack()
            }
        }

        Spacer(modifier = Modifier.height(49.dp))

    }
}