package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.local.model.ImageEntity
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostPaginationDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostResponseDto
import com.ccc.remind.data.source.remote.model.mind.vo.ImageFileVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindCardBookmarkVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindCardTagVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindCardVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindCommentVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindLikeVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindMemoVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindPostCardVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindTagVO
import com.ccc.remind.data.source.remote.model.user.UserVO
import com.ccc.remind.data.source.socket.model.AppendMindCommentDto
import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardBookmark
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindCardTag
import com.ccc.remind.domain.entity.mind.MindComment
import com.ccc.remind.domain.entity.mind.MindLike
import com.ccc.remind.domain.entity.mind.MindMemo
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.mind.MindPostCard
import com.ccc.remind.domain.entity.mind.MindPostList
import com.ccc.remind.domain.entity.mind.MindTag
import com.ccc.remind.domain.entity.user.User

fun MindPostResponseDto.toDomain() = MindPost(
    id = this.id,
    createdAt = this.createdAt,
    cards = this.cards.map { it.toDomain() },
    images = this.images.map { it.image.toDomain() },
    memo = this.memo?.toDomain(),
    user = this.user?.toDomain()
)

fun MindPostPaginationDto.toDomain() = MindPostList(
    page = this.page,
    lastPage = this.lastPage,
    data = this.data.map { it.toDomain() }
)

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

fun ImageFile.toEntity() = ImageEntity(
    id,
    fileName
)

fun ImageFile.toData() = ImageFileVO(
    id,
    fileName
)

fun ImageEntity.toDomain() = ImageFile(
    id,
    fileName
)

fun MindPostCardVO.toDomain() = MindPostCard(
    id = this.id,
    type = MindCardSelectType.valueOf(this.type),
    card = this.card.toDomain()
)

fun MindMemoVO.toDomain() = MindMemo(
    id = this.id,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    text = this.text,
    comments = this.comments.map { it.toDomain() }
)


fun MindCommentVO.toDomain() = MindComment(
    id = this.id,
    createdAt = this.createdAt,
    text = this.text,
    user = this.user.toDomain(),
    likes = this.likes.map { it.toDomain() }
)

fun AppendMindCommentDto.toDomain() = MindComment(
    id = this.id,
    createdAt = this.createdAt,
    text = this.text,
    user = this.user.toDomain(),
    likes = this.likes.map { it.toDomain() }
)

fun UserVO.toDomain() = User(
    id = this.id,
    displayName = this.displayName,
    profileImage = this.profileImage?.toDomain(),
    inviteCode = this.inviteCode
)

fun MindLikeVO.toDomain() = MindLike(
    id = this.id,
    createdAt = this.createdAt,
    user = this.user.toDomain()
)

fun MindCardBookmarkVO.toDomain() = MindCardBookmark(
    id = this.id,
    mindCard = this.mindCard.toDomain()
)