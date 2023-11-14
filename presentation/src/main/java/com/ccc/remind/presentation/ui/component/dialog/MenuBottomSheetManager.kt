package com.ccc.remind.presentation.ui.component.dialog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.model.ButtonModel
import com.ccc.remind.presentation.ui.component.model.ButtonPriority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuBottomSheetManager(
    private val scope: CoroutineScope
) {
    private val _openState = MutableStateFlow(false)
    private val openState: StateFlow<Boolean> = _openState.asStateFlow()
    private var buttons = listOf<ButtonModel>()
    private var useDefaultCancelButton = true
    private var onDismiss: (() -> Unit)? = null

    fun open() {
        scope.launch {
            _openState.value = true
        }
    }

    fun close() {
        scope.launch {
            _openState.value = false
        }
    }

    fun init(
        buttons: List<ButtonModel>,
        useDefaultCancelButton: Boolean = true,
        onDismiss: (() -> Unit)? = null
    ): MenuBottomSheetManager = this.apply {
        this.buttons = buttons
        this.useDefaultCancelButton = useDefaultCancelButton
        this.onDismiss = onDismiss
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun instance() {
        val openBottomSheet by openState.collectAsState()
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val buttonItems by remember {
            if (useDefaultCancelButton) {
                buttons = buttons.plus(
                    ButtonModel(
                        textResId = R.string.to_cancel,
                        priority = ButtonPriority.DEFAULT,
                        onClick = { close() }
                    )
                )
            }
            mutableStateOf(buttons)
        }


        MenuBottomSheet(
            openBottomSheet = openBottomSheet,
            onDismiss = {
                onDismiss?.invoke()
                close()
            },
            sheetState = sheetState,
            buttons = buttonItems
        )
    }
}