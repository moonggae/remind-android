package com.ccc.remind.data.source.remote.model.mind

data class MindCardVO(
    val id: Long,
    val name: String,
    val displayName: String,
    val description: String?,
    val tags: List<MindCardTagVO>,
    val imageFile: ImageFileVO?
)
