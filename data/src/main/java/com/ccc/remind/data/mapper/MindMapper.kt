package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.remote.model.mind.vo.ImageFileVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindCardTagVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindCardVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindTagVO
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