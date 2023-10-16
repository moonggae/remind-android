package com.ccc.remind.presentation.ui.component.pageComponent.memo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.MindComment
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.toFormatString
import java.util.UUID

@Composable
fun CommentList(
    modifier: Modifier = Modifier,
    comments: List<MindComment>,
    myUUID: UUID,
    onClickLike: (MindComment) -> Unit
) {
    val lastIndex = if(comments.lastIndex < 0) 0 else comments.lastIndex

    var columnState = rememberLazyListState(lastIndex)

    LaunchedEffect(comments.size) {
        columnState.scrollToItem(lastIndex)
    }

    LazyColumn(
        state = columnState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = modifier
    ) {
        itemsIndexed(comments.sortedBy { it.createdAt }) { index, item ->
            if (
                index == 0 ||
                comments[index - 1].createdAt.dayOfMonth != item.createdAt.dayOfMonth
            ) {
                LinedText(
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = item.createdAt.toFormatString(stringResource(R.string.yyyy_mm_dd_e)),
                        color = RemindMaterialTheme.colorScheme.fg_subtle,
                        style = RemindMaterialTheme.typography.regular_sm,
                    )
                }
            }

            if (item.user.id == myUUID) {
                MyComment(
                    text = item.text,
                    isLiked = item.likes.isNotEmpty(),
                    createdAt = item.createdAt
                )
            } else {
                OtherComment(
                    text = item.text,
                    isLiked = item.likes.isNotEmpty(),
                    createdAt = item.createdAt,
                    profileImageUrl = item.user.profileImage?.url,
                    modifier = Modifier.padding(bottom = 4.dp),
                ) {
                    onClickLike(item)
                }
            }
        }
    }
}