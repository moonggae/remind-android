package com.ccc.remind.domain.usecase.post

import com.ccc.remind.domain.repository.MindRepository
import javax.inject.Inject

class GetMindPostListUseCase @Inject constructor(
    private val mindRepository: MindRepository
) {
    operator fun invoke(page: Int) = mindRepository.getPostList(page)
}