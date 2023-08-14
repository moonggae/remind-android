package com.ccc.remind.data.source.remote

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

    @POST("mind/post")
    suspend fun postMindPost(@Body() requestDto: MindPostRequestDto): Response<MindPostResponseDto>

    @PUT("mind/post/{id}")
    suspend fun putMindPost(@Path("id") id: Int, @Body() requestDto: MindPostRequestDto): Response<MindPostResponseDto>

    @DELETE("mind/post/{id}")
    suspend fun deleteMindPost(@Path("id") id: Int)
}