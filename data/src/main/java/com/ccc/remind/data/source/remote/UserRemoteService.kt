package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.user.DisplayNameDto
import com.ccc.remind.data.source.remote.model.user.UserProfileResponseDto
import com.ccc.remind.data.source.remote.model.user.UserProfileUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserRemoteService {
    @GET("users/displayName")
    suspend fun fetchDisplayName() : Response<DisplayNameDto>

    @PATCH("users/displayName")
    suspend fun updateDisplayName(@Body() displayNameDto: DisplayNameDto)

    @GET("users/profile")
    suspend fun fetchUserProfile(): Response<UserProfileResponseDto>

    @PATCH("users/profile")
    suspend fun updateProfile(@Body profileUpdateDto: UserProfileUpdateDto)
}