package com.ccc.remind.presentation.ui.component.pageComponent.history.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.pageComponent.history.MindListTimestampType
import com.ccc.remind.presentation.ui.mindPost.PostViewType
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.toFormatString
import kotlinx.coroutines.launch

@Composable
fun PostMindListView(
    posts: List<MindPost>,
    navController: NavController,
    scrollState: LazyListState,
    timestampType: MindListTimestampType = MindListTimestampType.BY_DAY,
    timestampFormat: String? = null,
    itemBackgroundColor: Color = RemindMaterialTheme.colorScheme.bg_muted,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        // todo: performance
        state = scrollState,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        modifier = modifier
    ) {
        itemsIndexed(
            items = posts,
//                key = { _, item -> item.id }  // todo: conflict when load data
        ) { index, item ->
            val isByDayTimestampCondition = index == 0 || posts[index - 1].createdAt.dayOfMonth != item.createdAt.dayOfMonth
            if (
                (timestampType == MindListTimestampType.EVERYTIME) ||
                (timestampType == MindListTimestampType.BY_DAY && isByDayTimestampCondition)
            ) {
                Text(
                    text = item.createdAt.toFormatString(timestampFormat ?: "yyyy년 M월 d일"),
                    style = RemindMaterialTheme.typography.regular_md,
                    color = RemindMaterialTheme.colorScheme.fg_default
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            MindPostListItem(
                data = item,
                backgroundColor = itemBackgroundColor,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true)
                ) {
                    scope.launch {
                        navController.navigate("${Route.MindPost.Detail.name}?id=${item.id}&type=${PostViewType.DETAIL}")
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}