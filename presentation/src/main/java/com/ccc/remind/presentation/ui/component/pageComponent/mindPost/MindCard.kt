package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.domain.url
import kotlinx.coroutines.launch

@Composable
fun MindCard(
    modifier: Modifier = Modifier,
    mindCard: MindCard,
    selectType: MindCardSelectType? = null,
    isBookmarked: Boolean? = null,
    onClickBookmark: ((MindCard) -> Unit)? = null,
    showDisplayName: Boolean = true,
    bookmarkSize: Dp = 30.dp,
    cardSize: Dp? = null,
    onClick: (MindCard) -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    Box(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClick(mindCard) }
        ) {
            MindCardImage(
                url = mindCard.imageFile?.url ?: "",
                selectType = selectType,
                radius = 12.dp,
                size = cardSize
            )
            if(showDisplayName) {
                if (selectType != null)
                    Text(
                        text = mindCard.displayName,
                        style = RemindMaterialTheme.typography.bold_lg,
                        color = RemindMaterialTheme.colorScheme.accent_default
                    )
                else
                    Text(
                        text = mindCard.displayName,
                        style = RemindMaterialTheme.typography.regular_lg
                    )
            }
        }

        if(isBookmarked == true) {
            Image(
                painter = painterResource(R.drawable.ic_bookmark_on),
                contentDescription = stringResource(R.string.bookmark_on),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(bookmarkSize)
                    .padding(top = 8.dp, end = 8.dp)
                    .clickable { scope.launch { onClickBookmark?.invoke(mindCard) } }
            )
        } else if (isBookmarked == false) {
            Image(
                painter = painterResource(R.drawable.ic_bookmark_off),
                contentDescription = stringResource(R.string.bookmark_off),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(bookmarkSize)
                    .padding(top = 8.dp, end = 8.dp)
                    .clickable { scope.launch { onClickBookmark?.invoke(mindCard) } }
            )
        }
    }
}