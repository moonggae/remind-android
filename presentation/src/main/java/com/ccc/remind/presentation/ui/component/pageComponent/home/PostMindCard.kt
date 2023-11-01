package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ccc.remind.presentation.util.buildCoilRequest

@Composable
fun PostMindCard(
    modifier: Modifier = Modifier,
    mindCardUrl: String,
) {
    AsyncImage(
        model = buildCoilRequest(
            context = LocalContext.current,
            url = mindCardUrl
        ),
        contentDescription = "",
        modifier = modifier.then(Modifier.width(153.dp)),
        contentScale = ContentScale.FillWidth,
    )
}