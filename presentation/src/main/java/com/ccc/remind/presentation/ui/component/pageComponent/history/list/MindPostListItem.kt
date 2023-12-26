package com.ccc.remind.presentation.ui.component.pageComponent.history.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.presentation.ui.component.icon.RoundedTextIcon
import com.ccc.remind.presentation.ui.component.icon.UserProfileIcon
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.extensions.domain.url
import kotlinx.coroutines.launch

@Composable
fun MindPostListItem(
    modifier: Modifier = Modifier,
    data: MindPost
) {
    val scope = rememberCoroutineScope()
    var showCardTagCount by remember { mutableStateOf(data.cards.size) }

    LaunchedEffect(showCardTagCount) {}

    LaunchedEffect(data.cards.size) {
        showCardTagCount = data.cards.size
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = RemindMaterialTheme.colorScheme.bg_muted
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .then(modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 20.dp)
        ) {
            UserProfileIcon(
                size = 44.dp,
                modifier = Modifier.padding(end = 20.dp),
                imageUrl = data.user?.profileImage?.url
            )


            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false
            ) {
                items(count = showCardTagCount) { index ->
                    if (index < data.cards.size) { // note: socket으로 인한 update시 out of index 에러 방지
                        RoundedTextIcon(
                            text = data.cards[index].card.displayName,
                            color = RemindMaterialTheme.colorScheme.accent_default,
                            containerColor = RemindMaterialTheme.colorScheme.bg_default,
                            style = RemindMaterialTheme.typography.bold_md,
                            showBorder = true
                        )
                    }
                }
            }

            Spacer(modifier = Modifier
                .weight(1f)
                .onSizeChanged {
                    if (it.width <= 0 && showCardTagCount > 0) {
                        scope.launch { showCardTagCount -= 1 }
                    }
                })

            Icon(
                painter = painterResource(R.drawable.ic_arrow_light),
                contentDescription = stringResource(R.string.arrow_light_icon),
                tint = RemindMaterialTheme.colorScheme.accent_default,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}