package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.mind.dto.MindPostPaginationDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostRequestDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostResponseDto
import com.ccc.remind.data.source.remote.model.mind.vo.MindCardVO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MindRemoteService {
    @GET("mind/card")
    suspend fun fetchMindCards() : Response<List<MindCardVO>>

    @GET("mind/post/last")
    suspend fun fetchLastPostMind(): Response<MindPostResponseDto?>

    @GET("mind/post/last/friend")
    suspend fun fetchFriendLastPostMind(): Response<MindPostResponseDto?>

    @POST("mind/post")
    suspend fun postMindPost(@Body() requestDto: MindPostRequestDto): Response<MindPostResponseDto>

    @PUT("mind/post/{id}")
    suspend fun putMindPost(@Path("id") id: Int, @Body() requestDto: MindPostRequestDto): Response<MindPostResponseDto>

    @DELETE("mind/post/{id}")
    suspend fun deleteMindPost(@Path("id") id: Int)

    @POST("mind/post/request/friend")
    suspend fun postRequestFriendMind()

    @GET("mind/post/list/{page}")
    suspend fun fetchMindPostPagination(@Path("page") page: Int): Response<MindPostPaginationDto>

    @GET("mind/post/{id}")
    suspend fun fetchMindPost(@Path("id") id: Int): Response<MindPostResponseDto>
}