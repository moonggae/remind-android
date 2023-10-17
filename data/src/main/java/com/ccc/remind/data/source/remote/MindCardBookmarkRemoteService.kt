package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.mind.vo.MindCardBookmarkVO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MindCardBookmarkRemoteService {
    @GET("mind/card/bookmark")
    suspend fun fetchBookmarks(): Response<List<MindCardBookmarkVO>>

    @POST("mind/card/bookmark/{mindCardId}")
    suspend fun postBookmark(@Path("mindCardId") mindCardId: Int)

    @DELETE("mind/card/bookmark/{mindCardId}")
    suspend fun deleteBookmark(@Path("mindCardId") mindCardId: Int)
}