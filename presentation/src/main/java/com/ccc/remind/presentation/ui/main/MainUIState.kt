package com.ccc.remind.presentation.ui.main

data class MainUIState(
    val loading: Boolean = false,
    val error: String? = null,
    val isDenyNotification: Boolean? = null
)