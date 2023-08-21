package com.ccc.remind.domain.usecase.memo

import com.ccc.remind.domain.repository.MindMemoRepository
import javax.inject.Inject

class PostLikeUseCase @Inject constructor(private val mindMemoRepository: MindMemoRepository) {
    operator fun invoke(commentId: Int) = mindMemoRepository.postLike(commentId)
}