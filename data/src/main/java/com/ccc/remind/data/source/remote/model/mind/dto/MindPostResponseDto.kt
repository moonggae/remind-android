package com.ccc.remind.data.source.remote.model.mind.dto

import com.ccc.remind.data.source.remote.model.mind.vo.MindMemoVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindPostCardVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindPostImageVO
import com.ccc.remind.data.source.remote.model.user.UserVO
import java.time.ZonedDateTime

data class MindPostResponseDto(
    val id: Int,
    val createdAt: ZonedDateTime,
    val cards: List<MindPostCardVO>,
    val images: List<MindPostImageVO>,
    val memo: MindMemoVO?,
    val user: UserVO?
)