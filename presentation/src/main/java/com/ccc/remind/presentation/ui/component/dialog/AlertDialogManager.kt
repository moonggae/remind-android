package com.ccc.remind.presentation.ui.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.model.ButtonModel
import com.ccc.remind.presentation.ui.component.model.ButtonPriority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlertDialogManager(
    private val scope: CoroutineScope
) {
    private val _openState = MutableStateFlow(false)
    private val openState: StateFlow<Boolean> = _openState.asStateFlow()
    private var buttons = listOf<ButtonModel>()
    private var useDefaultCancelButton = true
    private var onDismiss: (() -> Unit)? = null
    private var contentResId: Int = -1
    private var titleResId: Int? = null

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
        titleResId: Int? = null,
        contentResId: Int,
        onDismiss: (() -> Unit)? = null
    ): AlertDialogManager = this.apply {
        this.buttons = buttons
        this.useDefaultCancelButton = useDefaultCancelButton
        this.titleResId = titleResId
        this.contentResId = contentResId
        this.onDismiss = onDismiss
    }

    @Composable
    fun instance() {
        val openDialog by openState.collectAsState()
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

        if (openDialog) {
            AlertDialog(
                titleText = titleResId?.let { stringResource(it) },
                contentText = stringResource(contentResId),
                onDismiss = {
                    onDismiss?.invoke()
                    close()
                },
                buttons = buttonItems
            )
        }
    }
}