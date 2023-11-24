package com.ccc.remind.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.button.MoveIconButton
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.pageComponent.home.EmptyMemoCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.EmptyPostMindCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.EmptyPostMindLabelCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.MindMemoCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.PostMindCard
import com.ccc.remind.presentation.ui.component.pageComponent.home.PostMindLabelBar
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.ViewCardsDetailTextButton
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun HomeMyView(
    modifier: Modifier = Modifier,
    postMind: MindPost?,
    displayName: String?,
    navController: NavController,
) {
    Column(modifier) {
        if (postMind == null) {
            EmptyPostMindCard(
                userDisplayName = displayName ?: stringResource(R.string.empty_user_display_name),
                onClickAddButton = { navController.navigate(Route.MindPost.SelectCard.name) }
            )
        } else {
            PostMindCard(
                mindCardUrl = postMind.cards.first().card.imageUrl ?: "",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(18.dp))

            PrimaryButton(
                text = stringResource(R.string.home_my_view_button_post_mind),
                textStyle = RemindMaterialTheme.typography.bold_lg,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .height(46.dp),
                onClick = { navController.navigate(Route.MindPost.SelectCard.name) }
            )
        }


        Spacer(modifier = Modifier.height(26.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.home_label_mind_history),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            if (postMind?.cards?.isNotEmpty() == true) {
                ViewCardsDetailTextButton(
                    navController = navController,
                    cardIds = postMind.cards.map { postCard -> postCard.card.id }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (postMind == null) {
            EmptyPostMindLabelCard()
        } else {
            PostMindLabelBar(cards = postMind.cards)
        }

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (postMind?.memo == null) 8.dp else 0.dp)
        ) {
            Text(
                text = stringResource(R.string.home_label_mind_memo),
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.fg_muted
            )

            if (postMind?.memo != null) {
                MoveIconButton {
                    navController.navigate(route = "${Route.MemoEdit.name}/${postMind.id}/${postMind.memo?.id ?: -1}/false")
                }
            }
        }


        when {
            postMind == null -> EmptyMemoCard()
            postMind.memo == null -> EmptyMemoCard {
                navController.navigate(route = "${Route.MemoEdit.name}/${postMind.id}/${-1}/false")
            }

            else -> MindMemoCard(
                text = postMind.memo?.text ?: "",
                modifier = Modifier.fillMaxHeight(),
                commentSize = postMind.memo?.comments?.size ?: 0
            )
        }
    }
}

