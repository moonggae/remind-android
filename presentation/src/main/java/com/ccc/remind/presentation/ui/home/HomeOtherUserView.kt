package com.ccc.remind.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.dialog.AlertDialog
import com.ccc.remind.presentation.ui.component.pageComponent.home.EmptyMemoCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.EmptyOtherPostMindCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.EmptyPostMindLabelCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.MindMemoCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.PostMindCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.PostMindLabelBar
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.ViewCardsDetailTextButton
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeOtherUserView(
    modifier: Modifier = Modifier,
    postMind: MindPost?,
    displayName: String?,
    navController: NavController,
    onRequestFriendMind: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var openAskMindAlertDialog by remember { mutableStateOf(false) }
    val openDialog: () -> Unit = {
        scope.launch {
            openAskMindAlertDialog = true
        }
    }
    val closeDialog: () -> Unit = {
        scope.launch {
            openAskMindAlertDialog = false
        }
    }


    Column(modifier) {
        if (postMind == null) {
            EmptyOtherPostMindCard(userDisplayName = displayName) {
                navController.navigate(Route.Invite.name)
            }
        } else {
            PostMindCard(
                mindCardUrl = postMind.cards.first().card.imageUrl ?: "",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(18.dp))

            PrimaryButton(
                text = stringResource(R.string.home_ask_mind_button),
                textStyle = RemindMaterialTheme.typography.bold_lg,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .height(46.dp),
                onClick = openDialog
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.home_label_mind_history),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            if (postMind?.cards?.isNotEmpty() == true) {
                ViewCardsDetailTextButton(
                    navController = navController,
                    cardIds = postMind.cards.map { postCard -> postCard.card.id }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (postMind == null) {
            EmptyPostMindLabelCard()
        } else {
            PostMindLabelBar(cards = postMind.cards)
        }

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (postMind?.memo == null) 8.dp else 0.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_label_mind_memo),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted,
            )

            if (postMind?.memo != null) {
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false
                ) {
                    IconButton({ navController.navigate("${Route.MemoEdit.name}/${postMind.id}/${postMind.memo?.id ?: -1}/true") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_light),
                            contentDescription = stringResource(id = R.string.arrow_light_icon),
                            tint = Color(0xFF686868),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }


        when (postMind?.memo) {
            null -> EmptyMemoCard()
            else -> MindMemoCard(
                text = postMind.memo?.text ?: "",
                modifier = Modifier.fillMaxHeight(),
                commentSize = postMind.memo?.comments?.size ?: 0
            )
        }
    }

    if(openAskMindAlertDialog) {
        AlertDialog(
            contentText = stringResource(R.string.home_ask_mind_dialog_content_text, displayName ?: ""),
            onClickConfirmButton = {
                onRequestFriendMind()
                closeDialog()
            },
            onClickCancelButton = closeDialog,
            onDismissRequest = closeDialog,
            buttonReverse = true,
            confirmLabelColor = RemindMaterialTheme.colorScheme.accent_default
        )
    }
}