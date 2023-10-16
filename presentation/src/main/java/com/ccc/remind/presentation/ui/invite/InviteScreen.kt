package com.ccc.remind.presentation.ui.invite

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.TextButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.layout.BackgroundedTextField
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.ClipboardUtil
import com.ccc.remind.presentation.util.Constants
import com.ccc.remind.presentation.util.Constants.INVITE_URL_PATH
import kotlinx.coroutines.launch

@Composable
@Preview
fun InviteScreenPreview() {
    InviteScreen()
}

/* TODO
- prevent to use my invite code
- prevent to make many friends
- delete friend
- prevent npe when fetch user profile by invite code
*/

@Composable
fun InviteScreen(
    navController: NavController = rememberNavController(),
    viewModel: InviteViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val sharedUiState by sharedViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(uiState.openedUserProfile) {
        if(uiState.openedUserProfile != null)
            navController.navigate(Route.Invite.Profile.name)
    }

    LaunchedEffect(navController.currentDestination) {
        if(navController.currentDestination?.route == Route.Invite.name) {
            viewModel.initRequestList()
        }
    }

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.invite_appbar_title),
                navController = navController
            )
        },
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.invite_label_use_invite_code),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_default,
        )

        BackgroundedTextField(
            value = uiState.inputInviteCode,
            onValueChange = viewModel::updateInputInviteCode,
            contentAlignment = Alignment.Center,
            textStyle = RemindMaterialTheme.typography.bold_xxl.copy(
                color = RemindMaterialTheme.colorScheme.fg_default,
                textAlign = TextAlign.Center
            ),
            padding = PaddingValues(horizontal = 4.dp),
        )

        TextButton(
            text = stringResource(R.string.invite_button_label_invite),
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.validInviteCode,
            onClick = viewModel::submitGetUserProfile
        )
        
        
        Spacer(modifier = Modifier.height(12.dp))


        Text(
            text = stringResource(R.string.invite_label_my_invite_code),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_default,
        )

        BackgroundedTextField(
            value = "${sharedUiState.user?.inviteCode}",
            onValueChange = {},
            readOnly = true,
            contentAlignment = Alignment.Center,
            textStyle = RemindMaterialTheme.typography.bold_xxl.copy(
                color = RemindMaterialTheme.colorScheme.fg_default,
                textAlign = TextAlign.Center
            ),
            padding = PaddingValues(horizontal = 4.dp),
            suffix = {
                IconButton(
                    onClick = {
                        scope.launch {
                            ClipboardUtil(context).copyText("invite code", "${sharedUiState.user?.inviteCode}")
                        }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_copy),
                        tint = RemindMaterialTheme.colorScheme.fg_muted,
                        contentDescription = stringResource(R.string.copy_icon)
                    )
                }
            }
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(
                text = stringResource(R.string.invite_button_label_copy_invite_url),
                modifier = Modifier.weight(1f),
                contentColor = RemindMaterialTheme.colorScheme.fg_default
            ) {
                scope.launch {
                    ClipboardUtil(context).copyUri("invite uri", Uri.parse("${Constants.BASE_URL}/${INVITE_URL_PATH}/${sharedUiState.user?.inviteCode}"))
                }
            }
            TextButton(
                text = stringResource(R.string.invite_button_label_share_invite_url),
                modifier = Modifier.weight(1f),
                containerColor = RemindMaterialTheme.colorScheme.accent_default,
                contentColor = RemindMaterialTheme.colorScheme.bg_default
            ) {
                scope.launch {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${Constants.BASE_URL}/${INVITE_URL_PATH}/${sharedUiState.user?.inviteCode}")
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)

                    context.startActivity(shareIntent)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        InviteRequestListView(
            navController = navController,
            viewModel = viewModel,
            sharedViewModel = sharedViewModel
        )
    }
}