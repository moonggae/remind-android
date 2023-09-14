package com.ccc.remind.presentation.ui.memo

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.SharedViewModel
import com.ccc.remind.presentation.ui.component.dialog.AlertDialog
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.pageComponent.memo.CommentInputBox
import com.ccc.remind.presentation.ui.component.pageComponent.memo.CommentList
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.MindMemoTextField
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.toFormatString
import kotlinx.coroutines.launch
import java.util.UUID

/*
TODO: delete comment, synchronize using web socket
 */

@Composable
fun MemoEditScreen(
    navController: NavController,
    viewModel: MemoEditViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStatus.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var openDeleteMemoAlertDialog by remember { mutableStateOf(false) }

    var isInitialized by remember { mutableStateOf(false) }
    LaunchedEffect(isInitialized) {
        if(!isInitialized) {
            isInitialized = true
            viewModel.fetchMemoData()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppBar(
            navController = navController,
            title = stringResource(R.string.mind_memo_edit_appbar_title)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            if (uiState.editType == MemoEditType.UPDATE) {
                Text(
                    text = "${uiState.openedMemo?.createdAt?.toFormatString("yyyy년 M월 d일")}",
                    style = RemindMaterialTheme.typography.bold_lg,
                    color = RemindMaterialTheme.colorScheme.fg_default
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${uiState.openedMemo?.createdAt?.toFormatString("a hh:mm")}",
                    style = RemindMaterialTheme.typography.regular_md,
                    color = RemindMaterialTheme.colorScheme.fg_muted
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            MindMemoTextField(
                value = uiState.memoText,
                enabled = true,
                onValueChange = viewModel::updateMemoText,
                modifier = Modifier.defaultMinSize(
                    minHeight = 128.dp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.End
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.editType == MemoEditType.UPDATE) {
                    Button(
                        onClick = {
                                  scope.launch {
                                      viewModel.submitUpdateMemo()
                                      Toast.makeText(context, "메모를 수정 했어요.", Toast.LENGTH_SHORT).show()
                                  }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RemindMaterialTheme.colorScheme.accent_bg
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width(49.dp)
                            .height(28.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.to_update),
                            style = RemindMaterialTheme.typography.regular_md,
                            color = RemindMaterialTheme.colorScheme.fg_muted
                        )
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                openDeleteMemoAlertDialog = true
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RemindMaterialTheme.colorScheme.bg_subtle
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width(49.dp)
                            .height(28.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.to_delete),
                            style = RemindMaterialTheme.typography.regular_md,
                            color = RemindMaterialTheme.colorScheme.fg_muted
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.submitPostMemo()
                                navController.popBackStack()
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RemindMaterialTheme.colorScheme.accent_bg
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width(49.dp)
                            .height(28.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.to_complete),
                            style = RemindMaterialTheme.typography.regular_md,
                            color = RemindMaterialTheme.colorScheme.fg_muted
                        )
                    }
                }
            }
        }

        if (uiState.editType == MemoEditType.UPDATE) {
            Divider(
                color = RemindMaterialTheme.colorScheme.bg_subtle,
                modifier = Modifier.padding(
                    top = 32.dp,
                )
            )
        }
        

        CommentList(
            comments = uiState.openedMemo?.comments ?: emptyList(),
            myUUID = sharedViewModel.uiState.value.user?.uuid ?: UUID.randomUUID(),
            onClickLike = { clickedComment ->
                if (clickedComment.likes.isEmpty()) {
                    viewModel.submitPostLike(clickedComment.id)
                } else {
                    viewModel.submitDeleteLike(clickedComment.likes.first().id)
                }
            },
            modifier = Modifier.weight(1f)
        )


        CommentInputBox(
            text = uiState.commentText,
            onChangedText = viewModel::updateCommentText,
            onClickSendButton = viewModel::submitPostComment
        )
    }



    if (openDeleteMemoAlertDialog) {
        AlertDialog(
            contentText = stringResource(R.string.mind_memo_edit_alert_delete),
            onClickConfirmButton = {
                scope.launch {
                    openDeleteMemoAlertDialog = false
                    viewModel.submitDeleteMemo {
                        navController.popBackStack()
                    }
                }
            },
            onClickCancelButton = { scope.launch { openDeleteMemoAlertDialog = false } },
            onDismissRequest = { scope.launch { openDeleteMemoAlertDialog = false } }
        )
    }
}