package com.ccc.remind.data.source.remote.model.mind.vo

data class MindCardVO(
    val id: Int,
    val name: String,
    val displayName: String,
    val description: String?,
    val tags: List<MindCardTagVO>,
    val imageFile: ImageFileVO?
)
