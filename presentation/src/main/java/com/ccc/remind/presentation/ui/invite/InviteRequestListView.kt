package com.ccc.remind.presentation.ui.invite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.button.TextButton
import com.ccc.remind.presentation.ui.component.dialog.AlertDialog
import com.ccc.remind.presentation.ui.component.pageComponent.user.UserProfileCard
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import kotlinx.coroutines.launch

@Composable
@Preview
fun InviteRequestScreenPreview() {
    InviteRequestListView()
}

@Composable
fun InviteRequestListView(
    navController: NavController = rememberNavController(),
    viewModel: InviteViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Column {
        if(uiState.friendRequests.isNotEmpty()) {
            Text(
                text = stringResource(R.string.invite_label_my_friend_request),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_default,
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(count = uiState.friendRequests.size) {index ->
                    RequestFriend(
                        displayName = uiState.friendRequests[index].receivedUser.displayName ?: "",
                        profileImageUrl = uiState.friendRequests[index].receivedUser.profileImage?.url,
                        onClickCancel = {
                            scope.launch {
                                viewModel.submitCancelFriendRequest(uiState.friendRequests[index].id)
                                viewModel.initRequestList()
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        if(uiState.receivedFriendRequest.isNotEmpty()) {
            Text(
                text = stringResource(R.string.invite_label_received_friend_request),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_default,
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(uiState.receivedFriendRequest.size) { index ->
                    ReceivedFriendRequest(
                        displayName = uiState.receivedFriendRequest[index].requestUser.displayName ?: "",
                        profileImageUrl = uiState.receivedFriendRequest[index].requestUser.profileImage?.url,
                        onClickAccept = {
                            scope.launch {
                                viewModel.submitAcceptFriendRequest(uiState.receivedFriendRequest[index].id)
                                sharedViewModel.refreshFriend()
                                navController.popBackStack()
                            }
                        },
                        onClickDeny = {
                            scope.launch {
                                viewModel.submitDenyFriendRequest(uiState.receivedFriendRequest[index].id)
                                viewModel.initRequestList()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ReceivedFriendRequest(
    modifier: Modifier = Modifier,
    profileImageUrl: String? = null,
    displayName: String,
    onClickAccept: () -> Unit = {},
    onClickDeny: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var showDenyAlertDialog by remember { mutableStateOf(false) }
    val closeDenyAlertDialog: () -> Unit = {
        scope.launch {
            showDenyAlertDialog = false
        }
    }
    val openDenyAlertDialog: () -> Unit = {
        scope.launch {
            showDenyAlertDialog = true
        }
    }

    Row(
        modifier = modifier,
    ) {
        UserProfileCard(
            profileImageUrl = profileImageUrl,
            displayName = displayName,
            showTextSuffix = false
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            modifier = Modifier
                .height(32.dp)
                .width(50.dp),
            text = stringResource(R.string.to_accept),
            contentPadding = PaddingValues(0.dp),
            style = RemindMaterialTheme.typography.regular_md,
            onClick = onClickAccept
        )

        Spacer(modifier = Modifier.width(12.dp))

        TextButton(
            modifier = Modifier
                .height(32.dp)
                .width(50.dp),
            text = stringResource(R.string.to_deny),
            contentPadding = PaddingValues(0.dp),
            style = RemindMaterialTheme.typography.regular_md,
            containerColor = RemindMaterialTheme.colorScheme.warn,
            onClick = openDenyAlertDialog
        )
    }

    if(showDenyAlertDialog) {
        AlertDialog(
            contentText = stringResource(R.string.invite_alert_deny_friend_request),
            onClickConfirmButton = {
                onClickDeny()
                closeDenyAlertDialog()
            },
            onClickCancelButton = closeDenyAlertDialog,
            onDismissRequest = closeDenyAlertDialog
        )
    }
}

@Composable
fun RequestFriend(
    modifier: Modifier = Modifier,
    profileImageUrl: String? = null,
    displayName: String,
    onClickCancel: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var showCancelAlertDialog by remember { mutableStateOf(false) }
    val openCancelAlertDialog: () -> Unit = {
        scope.launch {
            showCancelAlertDialog = true
        }
    }
    val closeCancelAlertDialog: () -> Unit = {
        scope.launch {
            showCancelAlertDialog = false
        }
    }

    Row(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        UserProfileCard(
            profileImageUrl = profileImageUrl,
            displayName = displayName,
            showTextSuffix = false
        )

        TextButton(
            modifier = Modifier
                .height(32.dp)
                .width(50.dp),
            text = stringResource(R.string.to_cancel),
            contentPadding = PaddingValues(0.dp),
            style = RemindMaterialTheme.typography.regular_md,
            onClick = openCancelAlertDialog
        )
    }

    if(showCancelAlertDialog) {
        AlertDialog(
            contentText = stringResource(R.string.invite_alert_cancel_friend_request),
            onClickConfirmButton = {
                onClickCancel()
                closeCancelAlertDialog()
            },
            onClickCancelButton = closeCancelAlertDialog,
            onDismissRequest = closeCancelAlertDialog
        )
    }
}