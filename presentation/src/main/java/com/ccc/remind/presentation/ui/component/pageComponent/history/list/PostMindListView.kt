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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.mindPost.PostViewType
import kotlinx.coroutines.launch

@Composable
fun PostMindListView(
    posts: List<MindPost>,
    navController: NavController,
    scrollState: LazyListState,
    showDate: Boolean = true
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        // todo: performance
        state = scrollState,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
    ) {
        itemsIndexed(
            items = posts,
//                key = { _, item -> item.id }  // todo: conflict when load data
        ) { index, item ->
            if (
                showDate &&
                (index == 0 ||
                        posts[index - 1].createdAt.dayOfMonth != item.createdAt.dayOfMonth)
            ) {
                PostDateLabel(item.createdAt)
                Spacer(modifier = Modifier.height(8.dp))
            }


            MindPostListItem(
                data = item,
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
    }
}