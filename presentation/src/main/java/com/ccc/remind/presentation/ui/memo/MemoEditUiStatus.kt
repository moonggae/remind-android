package com.ccc.remind.presentation.ui.memo

import com.ccc.remind.domain.entity.mind.MindMemo

data class MemoEditUiStatus(
    val openedMemo: MindMemo? = null,
    val memoText: String = "",
    val commentText: String = "",
    val postId: Int? = null
) {
    val editType: MemoEditType
        get() = if(openedMemo == null) MemoEditType.POST else MemoEditType.UPDATE
}

enum class MemoEditType {
    POST,
    UPDATE
}