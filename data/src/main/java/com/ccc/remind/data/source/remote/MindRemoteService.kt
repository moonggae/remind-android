package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.mind.MindCardVO
import retrofit2.Response
import retrofit2.http.GET

interface MindRemoteService {
    @GET("mind/card")
    suspend fun fetchMindCards() : Response<List<MindCardVO>>
}