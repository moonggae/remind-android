package com.ccc.remind.domain.usecase.post

import android.util.Log
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.repository.MindRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


// todo: to refactor pagination
class GetMindPostListUseCase @Inject constructor(
    private val mindRepository: MindRepository
) {
    private var currentPage: Int = 0
    private var lastPage: Int? = null
    val posts = mindRepository.mindPosts

    fun initObserver(scope: CoroutineScope) = mindRepository.observeSocket(scope)

    suspend fun clearCache() {
        mindRepository.clearCachedPosts()
    }

    suspend operator fun invoke(): StateFlow<List<MindPost>> {
        Log.d("TAG", "GetMindPostListUseCase - invoke")
        val postList = mindRepository.getList(currentPage)
        postList.collect {
            currentPage = it.page
            lastPage = it.lastPage
        }

        return posts
    }

    // return value tells getting more data or not
    suspend fun next(): Boolean {
        Log.d("TAG", "GetMindPostListUseCase - next")
        if(lastPage == null) return false
        if(currentPage <= lastPage!!) {
            mindRepository.getList(++currentPage).collect {
                currentPage = it.page
                lastPage = it.lastPage
            }
            return true
        }
        return false
    }
}