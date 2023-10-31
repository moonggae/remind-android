package com.ccc.remind.data.source.socket.model

import com.ccc.remind.data.source.remote.model.mind.vo.MindLikeVO
import com.ccc.remind.data.source.remote.model.mind.vo.MindMemoVO
import com.ccc.remind.data.source.remote.model.user.UserVO
import java.time.ZonedDateTime

data class AppendMindCommentDto(
    val id: Int,
    val createdAt: ZonedDateTime,
    val text: String,
    val user: UserVO,
    val likes: List<MindLikeVO>,
    val memo: MindMemoVO
)