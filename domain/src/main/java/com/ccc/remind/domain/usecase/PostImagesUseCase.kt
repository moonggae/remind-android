package com.ccc.remind.domain.usecase

import android.content.Context
import android.net.Uri
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.repository.ImageRepository
import javax.inject.Inject

class PostImagesUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(context: Context, uris: List<Uri>): List<ImageFile> =
        imageRepository.postImages(context, uris)
}