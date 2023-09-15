package com.ccc.remind.presentation.ui.invite

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.ClipboardUtil
import com.ccc.remind.presentation.util.Constants
import kotlinx.coroutines.launch

@Composable
@Preview
fun InviteScreenPreview() {
    InviteScreen()
}

@Composable
fun InviteScreen(
    navController: NavController = rememberNavController(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val uiState by sharedViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.invite_appbar_title),
                navController = navController
            )
        },
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(R.string.invite_label_invite_code),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_default,
        )

        BackgroundedTextField(
            value = "${uiState.user?.inviteCode}",
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
                            ClipboardUtil(context).copyText("invite code", "${uiState.user?.inviteCode}")
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
                    ClipboardUtil(context).copyUri("invite uri", Uri.parse("${Constants.BASE_URL}/app/android/${uiState.user?.inviteCode}"))
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
                        putExtra(Intent.EXTRA_TEXT, "${Constants.BASE_URL}/app/android/${uiState.user?.inviteCode}")
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)

                    context.startActivity(shareIntent)
                }
            }
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}