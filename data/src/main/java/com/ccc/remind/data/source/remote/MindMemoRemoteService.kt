package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.mind.dto.MindCommentPostRequestDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindLikeRequestDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindMemoPatchRequestDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindMemoPostRequestDto
import com.ccc.remind.data.source.remote.model.mind.vo.MindCommentVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindLikeVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindMemoVO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MindMemoRemoteService {
    @GET("mind/post/memo/{id}")
    suspend fun fetchMemo(@Path("id") id: Int): Response<MindMemoVO>

    @POST("mind/post/memo")
    suspend fun postMemo(@Body dto: MindMemoPostRequestDto): Response<MindMemoVO>

    @PATCH("mind/post/memo/{id}")
    suspend fun patchMemo(@Path("id") id: Int, @Body dto: MindMemoPatchRequestDto): Response<MindMemoVO>

    @DELETE("mind/post/memo/{id}")
    suspend fun deleteMemo(@Path("id") id: Int)

    @POST("mind/post/memo/comment")
    suspend fun postComment(@Body dto: MindCommentPostRequestDto): Response<MindCommentVO>

    @POST("mind/post/memo/comment/like")
    suspend fun postLike(@Body dto: MindLikeRequestDto): Response<MindLikeVO>

    @DELETE("mind/post/memo/comment/like/{id}")
    suspend fun deleteLike(@Path("id") id: Int)
}