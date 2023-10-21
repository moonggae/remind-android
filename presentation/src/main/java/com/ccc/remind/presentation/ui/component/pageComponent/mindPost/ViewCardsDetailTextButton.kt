package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ViewCardsDetailTextButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.to_detail_view),
    cardIds: List<Int>,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    CompositionLocalProvider( // TextButton padding 제거
        LocalMinimumInteractiveComponentEnforcement provides false
    ) {
        TextButton(
            contentPadding = PaddingValues(0.dp),
            modifier = modifier.then(
                Modifier
                    .padding(0.dp)
                    .height(30.dp)
            ),
            onClick = {
                scope.launch {
                    var params = ""
                    cardIds.forEachIndexed { index, id ->
                        params += if(index == 0) { "?cardId=${id}" } else "&cardId=${id}"
                    }
                    navController.navigate(Route.MindCard.DetailCardList.name + params)
                }
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    style = RemindMaterialTheme.typography.regular_lg,
                    color = RemindMaterialTheme.colorScheme.accent_default
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_light),
                    contentDescription = stringResource(R.string.arrow_light_icon),
                    tint = RemindMaterialTheme.colorScheme.accent_default,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}