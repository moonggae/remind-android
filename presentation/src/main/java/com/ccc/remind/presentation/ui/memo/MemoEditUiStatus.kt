package com.ccc.remind.presentation.ui.memo

import com.ccc.remind.domain.entity.mind.MindMemo

data class MemoEditUiStatus(
    val openedMemo: MindMemo? = null,
    val memoText: String = "",
    val commentText: String = "",
    val postId: Int? = null,
    val initData: MemoEditInitialData? = null,
) {
    val editType: MemoEditType
        get() = when {
            this.initData?.isFriend == true -> MemoEditType.FRIEND
            this.openedMemo == null -> MemoEditType.POST
            else -> MemoEditType.UPDATE
        }
}

data class MemoEditInitialData(
    val postId: Int,
    val memoId: Int?,
    val isFriend: Boolean? = null
)

enum class MemoEditType {
    POST,
    UPDATE,
    FRIEND
}