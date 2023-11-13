package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.user.DisplayNameDto
import com.ccc.remind.data.source.remote.model.user.UpdateFCMTokenRequestDto
import com.ccc.remind.data.source.remote.model.user.UserProfileUpdateDto
import com.ccc.remind.data.source.remote.model.user.UserVO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserRemoteService {
    @PATCH("users/displayName")
    suspend fun updateDisplayName(@Body() displayNameDto: DisplayNameDto)

    @GET("users/profile")
    suspend fun fetchUserProfile(): Response<UserVO?>

    @GET("users/{id}")
    suspend fun fetchUser(@Path("id") id: String): Response<UserVO?>

    @GET("/users/inviteCode/{inviteCode}")
    suspend fun fetchUserByInviteCode(@Path("inviteCode") inviteCode: String): Response<UserVO?>

    @PATCH("users/profile")
    suspend fun updateProfile(@Body profileUpdateDto: UserProfileUpdateDto)

    @PATCH("notification/token")
    suspend fun updateFCMToken(@Body token: UpdateFCMTokenRequestDto)
}