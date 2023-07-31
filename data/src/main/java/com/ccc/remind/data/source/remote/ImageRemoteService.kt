package com.ccc.remind.data.source.remote

import com.ccc.remind.data.source.remote.model.mind.vo.ImageFileVO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageRemoteService {
    @POST("image")
    @Multipart
    suspend fun postImages(@Part files: List<MultipartBody.Part>): Response<List<ImageFileVO>>
}