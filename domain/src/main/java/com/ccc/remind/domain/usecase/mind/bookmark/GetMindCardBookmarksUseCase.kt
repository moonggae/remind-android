package com.ccc.remind.domain.usecase.mind.bookmark

import com.ccc.remind.domain.repository.MindCardBookmarkRepository
import javax.inject.Inject

class GetMindCardBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: MindCardBookmarkRepository
) {
    operator fun invoke() = bookmarkRepository.getBookmarks()
}