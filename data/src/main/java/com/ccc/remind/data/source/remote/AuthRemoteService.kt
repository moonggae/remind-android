package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.user.LoginRequest
import com.ccc.remind.data.source.remote.model.user.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRemoteService {
    @POST("auth/login/kakao")
    suspend fun loginKakao(@Body() loginRequest: LoginRequest): Response<TokenResponse>
    @POST("auth/refresh")
    suspend fun getNewToken(@Header("authorization") authorizationHeader: String): Response<TokenResponse>
}