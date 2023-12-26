package com.ccc.remind.presentation.ui.component.pageComponent.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.history.HistoryViewType
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun ItemListTypeSelector(
    selectedType: HistoryViewType,
    onTypeChanged: (type: HistoryViewType) -> Unit
) {
    val buttonColor = IconButtonDefaults.iconToggleButtonColors(
        contentColor = RemindMaterialTheme.colorScheme.fg_subtle,
        checkedContentColor = RemindMaterialTheme.colorScheme.accent_default
    )

    Row(
        modifier = Modifier.background(
            color = RemindMaterialTheme.colorScheme.bg_muted,
            shape = RoundedCornerShape(28.dp)
        )
    ) {
        IconToggleButton(
            checked = selectedType == HistoryViewType.CALENDAR,
            colors = buttonColor,
            onCheckedChange = { onTypeChanged(HistoryViewType.CALENDAR) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_calendar_item),
                contentDescription = stringResource(R.string.mind_history_view_type_selector_calendar)
            )
        }

        IconToggleButton(
            checked = selectedType == HistoryViewType.LIST,
            colors = buttonColor,
            onCheckedChange = { onTypeChanged(HistoryViewType.LIST) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_item_list),
                contentDescription = stringResource(R.string.mind_history_view_type_selector_list)
            )
        }
    }
}