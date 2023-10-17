package com.ccc.remind.domain.usecase.mind.bookmark

import com.ccc.remind.domain.repository.MindCardBookmarkRepository
import javax.inject.Inject

class DeleteMindCardBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: MindCardBookmarkRepository
) {
    suspend operator fun invoke(id: Int) = bookmarkRepository.deleteBookmark(id)
}