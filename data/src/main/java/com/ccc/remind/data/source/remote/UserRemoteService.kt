package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.user.DisplayNameDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserRemoteService {
    @GET("users/displayName")
    suspend fun fetchDisplayName() : Response<DisplayNameDto>

    @PATCH("users/displayName")
    suspend fun updateDisplayName(@Body() displayNameDto: DisplayNameDto)
}