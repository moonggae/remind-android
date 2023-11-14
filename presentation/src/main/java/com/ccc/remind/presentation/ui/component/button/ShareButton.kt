package com.ccc.remind.presentation.ui.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R

@Composable
fun ShareButton(
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = onClick
    ) {
        Icon(
            painterResource(id = R.drawable.ic_share),
            contentDescription = stringResource(R.string.share)
        )
    }
}