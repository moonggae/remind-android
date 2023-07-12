package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.remote.model.mind.ImageFileVO
import com.ccc.remind.data.source.remote.model.mind.MindCardTagVO
import com.ccc.remind.data.source.remote.model.mind.MindCardVO
import com.ccc.remind.data.source.remote.model.mind.MindTagVO
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardTag
import com.ccc.remind.domain.entity.mind.MindTag

fun MindCardVO.toDomain() = MindCard(
    id,
    name,
    displayName,
    description,
    tags.map { it.toDomain() },
    imageFile?.toDomain()
)


fun MindCardTagVO.toDomain() = MindCardTag(
    id,
    indicator,
    tag.toDomain()
)

fun MindTagVO.toDomain() = MindTag(
    id,
    name,
    displayName,
    description
)

fun ImageFileVO.toDomain() = ImageFile(
    id,
    fileName
)