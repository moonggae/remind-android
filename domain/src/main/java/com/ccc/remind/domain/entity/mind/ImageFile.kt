package com.ccc.remind.domain.entity.mind

import java.util.UUID

data class ImageFile(
    val id: UUID,
    val fileName: String
) {
    companion object {
        private const val urlPrefix = "http://10.0.2.2/image"
        fun fromUrl(url: String): ImageFile? {
            val parsedId = url.replace("${urlPrefix}/", "")
            val uuid = try {
                UUID.fromString(parsedId)
            } catch (_: IllegalArgumentException) {
                null
            } ?: return null

            return ImageFile(
                id = uuid,
                fileName = ""
            )
        }
    }


    val url: String
        get() = "$urlPrefix/$id"
}