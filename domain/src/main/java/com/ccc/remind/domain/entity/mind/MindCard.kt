package com.ccc.remind.domain.entity.mind

data class MindCard(
    val id: Long,
    val name: String,
    val displayName: String,
    val description: String?,
    val tags: List<MindCardTag>,
    val imageFile: ImageFile?
) {
    val imageUrl: String?
        get() =
            if(imageFile?.id != null)
                "http://10.0.2.2:3000/image/${imageFile.id}"
            else null
}