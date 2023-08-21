package com.ccc.remind.domain.usecase.memo

import com.ccc.remind.domain.repository.MindMemoRepository
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(private val mindMemoRepository: MindMemoRepository) {
    operator fun invoke(memoId: Int, text: String) = mindMemoRepository.postComment(memoId, text)
}