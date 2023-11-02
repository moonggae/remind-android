package com.ccc.remind.presentation.ui.component.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun AppBar(
    navController: NavController? = null,
    enableBack: Boolean = true,
    title: String,
    isExit: Boolean = false,
    padding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
    suffix: @Composable() (RowScope.() -> Unit)? = null
) {
    Row(
        modifier = Modifier.padding(padding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start)
        ) {
            if (navController != null && enableBack) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = if(isExit) R.drawable.ic_x else R.drawable.ic_arrow_left),
                        contentDescription = stringResource(id = if(isExit) R.string.exit else R.string.arrow_left),
                        tint = RemindMaterialTheme.colorScheme.fg_muted,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }


            Text(
                text = title,
                style = RemindMaterialTheme.typography.bold_xl,
                color = RemindMaterialTheme.colorScheme.fg_default
            )

            suffix?.let { suffix ->
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    content = suffix
                )
            }
        }
    }
}