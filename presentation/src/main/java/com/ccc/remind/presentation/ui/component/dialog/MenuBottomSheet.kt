package com.ccc.remind.presentation.ui.component.dialog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.component.button.TextFillButton
import com.ccc.remind.presentation.ui.component.model.ButtonModel
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBottomSheet(
    openBottomSheet: Boolean,
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    buttons: List<ButtonModel>
) {
    val scope = rememberCoroutineScope()

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            padding = PaddingValues(bottom = 30.dp, start = 20.dp, end = 20.dp)
        ) {
            buttons.forEachIndexed { index, button ->
                TextFillButton(
                    text = stringResource(button.textResId),
                    contentColor = button.priority.getColor(),
                    onClick = { scope.launch { button.onClick() } }
                )

                if (index < buttons.size - 1) {
                    Divider(
                        thickness = 0.5.dp,
                        color = RemindMaterialTheme.colorScheme.fg_muted
                    )
                }
            }
        }
    }
}