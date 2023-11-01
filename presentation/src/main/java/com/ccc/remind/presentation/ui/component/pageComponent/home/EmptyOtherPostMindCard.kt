package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.button.OutlinedTextButton
import com.ccc.remind.presentation.ui.component.container.BackgroundContainer
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun EmptyOtherPostMindCard(
    userDisplayName: String?,
    onClickAddButton: () -> Unit
) {
    val text: String =
        if(userDisplayName == null)
            "감정을 공유하고 싶은\n상대가 있나요?"
        else
            stringResource(id = R.string.home_empty_other_mind_box_message, userDisplayName)



    BackgroundContainer {
        Column(
            modifier = Modifier.padding(vertical = 68.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            if (userDisplayName == null) {
                OutlinedTextButton(
                    text = "초대하기",
                    modifier = Modifier.height(30.dp),
                    onClick = onClickAddButton
                )
            }
        }
    }
}