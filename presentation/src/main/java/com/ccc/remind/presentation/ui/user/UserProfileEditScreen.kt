package com.ccc.remind.presentation.ui.user

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserDisplayNameTextField
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserPictureEditButton
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun UserProfileEditScreen(
    navController: NavController = rememberNavController(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val sharedUiState by sharedViewModel.uiState.collectAsState()

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
            modifier = Modifier.align(CenterHorizontally)
        ) {

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.user_profile_edit_label_display_name),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_muted
        )

        Spacer(modifier = Modifier.height(12.dp))

        var displayName by remember { mutableStateOf("") }

        UserDisplayNameTextField(
            value = displayName,
            onValueChange = { displayName = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(text = stringResource(id = R.string.to_save)) {
            
        }

        Spacer(modifier = Modifier.height(49.dp))

    }
}