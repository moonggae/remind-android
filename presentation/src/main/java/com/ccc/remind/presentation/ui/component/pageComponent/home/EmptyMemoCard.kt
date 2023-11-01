package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyMemoCard(
    onClickAddButton: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(size = 20.dp))
            .background(RemindMaterialTheme.colorScheme.bg_muted)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(id = R.string.home_empty_mind_memo),
            style = RemindMaterialTheme.typography.regular_lg
        )

        if (onClickAddButton != null) {
            Spacer(modifier = Modifier.height(12.dp))

            CompositionLocalProvider(
                LocalMinimumInteractiveComponentEnforcement provides false
            ) {
                IconButton(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = RemindMaterialTheme.colorScheme.fg_subtle),
                    onClick = onClickAddButton
                ) {
                    Image(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
                }
            }

        }
    }
}