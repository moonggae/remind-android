package com.ccc.remind.presentation.ui.component.model

import androidx.annotation.StringRes

data class ButtonModel(
    @StringRes val textResId: Int,
    val onClick: () -> Unit,
    val priority: ButtonPriority = ButtonPriority.DEFAULT
)