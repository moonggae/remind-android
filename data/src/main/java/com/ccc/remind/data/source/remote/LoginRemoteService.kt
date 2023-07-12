package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.DisplayNameDto
import com.ccc.remind.data.source.remote.model.LoginRequest
import com.ccc.remind.data.source.remote.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface LoginRemoteService {
    @POST("auth/login/kakao")
    suspend fun loginKakao(@Body() loginRequest: LoginRequest): Response<LoginResponse>

    @GET("users/displayName")
    suspend fun fetchDisplayName() : Response<DisplayNameDto>

    @PATCH("users/displayName")
    suspend fun updateDisplayName(@Body() displayNameDto: DisplayNameDto)
}