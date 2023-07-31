package com.ccc.remind.domain.entity.mind

import java.util.UUID

data class ImageFile(
    val id: UUID,
    val fileName: String
) {
    val url: String
        get() = "http://10.0.2.2:3000/image/$id"
}