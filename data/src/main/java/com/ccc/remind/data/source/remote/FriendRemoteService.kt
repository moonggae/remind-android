package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.friend.FriendRequestsDto
import com.ccc.remind.data.source.remote.model.friend.ReceivedFriendRequestDto
import com.ccc.remind.data.source.remote.model.user.UserVO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendRemoteService {
    @GET("/friend")
    suspend fun fetchFriend(): Response<UserVO?>

    @POST("/friend/request/{inviteCode}")
    suspend fun postFriendRequest(@Path("inviteCode") inviteCode: String)

    @GET("/friend/requests")
    suspend  fun fetchReceivedFriendRequests(): Response<List<ReceivedFriendRequestDto>>

    @GET("/friend/myRequests")
    suspend  fun fetchFriendRequests(): Response<List<FriendRequestsDto>>

    @DELETE("/friend/request/{requestId}")
    suspend fun deleteFriendRequest(@Path("requestId") requestId: Int)

    @POST("/friend/accept/{requestId}")
    suspend fun postAcceptFriendRequest(@Path("requestId") requestId: Int)

    @POST("/friend/deny/{requestId}")
    suspend fun postDenyFriendRequest(@Path("requestId") requestId: Int)

    @DELETE("/friend")
    suspend fun deleteFriend()
}