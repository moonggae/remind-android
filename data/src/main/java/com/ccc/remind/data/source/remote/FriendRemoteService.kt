package com.ccc.remind.data.source.remote

import com.ccc.remind.domain.entity.user.UserProfile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FriendRemoteService {
    @GET("/friend/user/{inviteCode}")
    suspend fun fetchUserProfile(@Path("inviteCode") inviteCode: String): Response<UserProfile>
}