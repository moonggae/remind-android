package com.ccc.remind.domain.entity.mind

data class MindCard(
    val id: Int,
    val name: String,
    val displayName: String,
    val description: String?,
    val tags: List<MindCardTag>,
    val imageFile: ImageFile?
)