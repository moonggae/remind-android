package com.ccc.remind.presentation.ui.component.mindPost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.button.ToggleButton
import com.ccc.remind.presentation.ui.component.model.IToggleValue
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun <T: IToggleValue> MindCardListFilterBar(
    filterValues: List<T>,
    selectedFilters: List<T>,
    onClickFilter: (T) -> Unit,
    onClickRefresh: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            modifier = Modifier
                .padding(0.dp)
                .height(30.dp),
        ) {
            filterValues.map { value ->
                ToggleButton(
                    selectedValues = selectedFilters,
                    value = value,
                    onClick = onClickFilter
                )
            }
        }

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onClickRefresh
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = stringResource(R.string.refresh_button),
                tint = RemindMaterialTheme.colorScheme.accent_default
            )
        }
    }
}