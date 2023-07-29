package com.ccc.remind.presentation.ui.component.model

enum class MindFilter(
    override val text: String
) : IToggleValue {
    ALL("전체"),
    POSITIVE("긍정"),
    NEGATIVE("부정"),
    NORMAL("보통")
}