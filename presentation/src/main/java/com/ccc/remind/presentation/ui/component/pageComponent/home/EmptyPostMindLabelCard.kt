package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.container.BackgroundContainer
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun EmptyPostMindLabelCard() {
    BackgroundContainer(modifier = Modifier.padding(vertical = 32.dp)) {
        Text(
            text = stringResource(id = R.string.home_empty_mind_history),
            style = RemindMaterialTheme.typography.regular_lg
        )
    }
}