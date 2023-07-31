package com.ccc.remind.domain.repository

import android.content.Context
import android.net.Uri
import com.ccc.remind.domain.entity.mind.ImageFile

interface ImageRepository {
    suspend fun postImages(context: Context, uris: List<Uri>): List<ImageFile>
}