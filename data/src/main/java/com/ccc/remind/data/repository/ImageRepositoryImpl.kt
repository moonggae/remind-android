package com.ccc.remind.data.repository

import android.content.Context
import android.net.Uri
import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.ImageRemoteService
import com.ccc.remind.data.util.ContentUriRequestBody
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.repository.ImageRepository
import okhttp3.MultipartBody

class ImageRepositoryImpl(
    private val imageRemoteService: ImageRemoteService
): ImageRepository {

    override suspend fun postImages(context: Context, uris: List<Uri>): List<ImageFile> {
        val imageParts = uris.map { uri ->
            val requestBody = ContentUriRequestBody(context, uri)
            MultipartBody.Part.createFormData("files", requestBody.getFileName(), requestBody)
        }

        val response = imageRemoteService.postImages(imageParts).body()?.map { it.toDomain() }
        return response ?: emptyList()
    }
}