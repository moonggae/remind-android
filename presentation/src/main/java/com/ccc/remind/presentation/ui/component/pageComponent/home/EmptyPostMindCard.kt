package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.container.BackgroundContainer
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun EmptyPostMindCard(
    userDisplayName: String,
    onClickAddButton: () -> Unit
) {
    BackgroundContainer {
        Column(
            modifier = Modifier.padding(vertical = 68.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.home_empty_mind_box_message, userDisplayName),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = RemindMaterialTheme.colorScheme.fg_subtle, shape = CircleShape),
                onClick = onClickAddButton
            ) {
                Image(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
            }
        }
    }
}