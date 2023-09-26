package com.ccc.remind.presentation.ui.home

import com.ccc.remind.domain.entity.mind.MindPost

data class HomeUiStatus(
    val post: MindPost? = null,
    val friendPost: MindPost? = null
)