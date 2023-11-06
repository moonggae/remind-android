package com.ccc.remind.domain.usecase.post

import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.repository.MindRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class GetMindPostListUseCase @Inject constructor(
    private val mindRepository: MindRepository
) {
    private var currentPage: Int = 0
    private var lastPage: Int? = null
    val posts = mindRepository.postsFlow

    fun initObserver(scope: CoroutineScope) = mindRepository.observeSocket(scope)

    suspend fun get(): SharedFlow<List<MindPost>> {
        val postList = mindRepository.getPostList(currentPage)
        postList.collect {
            currentPage = it.page
            lastPage = it.lastPage
        }

        return posts
    }

    // return value tells getting more data or not
    suspend fun next(): Boolean {
        if(lastPage == null) return false
        if(currentPage >= lastPage!!) {
            mindRepository.getPostList(++currentPage).collect {
                currentPage = it.page
                lastPage = it.lastPage
            }
            return true
        }
        return false
    }
}