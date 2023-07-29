package com.ccc.remind.presentation.ui.component.mindPost

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ccc.remind.presentation.ui.component.model.MindCardSelectType
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme


@Composable
fun MindCardImage(
    modifier : Modifier = Modifier,
    url: String,
    selectType: MindCardSelectType? = null
) {

    val appliedModifier = setupMindCardImageModifier(
        modifier = modifier,
        selectType = selectType
    )

    Box(modifier = appliedModifier) {
        val context = LocalContext.current
        val request = setupImageCardRequest(context, url)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            AsyncImage(
                model = request,
                contentDescription = "",
                imageLoader = context.imageLoader,
            )
        }

        if (selectType == MindCardSelectType.MAIN)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        end = 8.dp,
                        bottom = 9.dp
                    ),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End,
            ) {
                MindCardTag(text = "대표")
            }
    }
}

@Composable
private fun setupMindCardImageModifier(
    modifier: Modifier = Modifier,
    selectType: MindCardSelectType? = null
): Modifier {
    val backgroundColor: Color
    val borderColor: Color
    val borderWidth: Dp

    if (selectType != null) {
        backgroundColor = RemindMaterialTheme.colorScheme.accent_bg
        borderColor = RemindMaterialTheme.colorScheme.accent_default
        borderWidth = 2.dp
    } else {
        backgroundColor = RemindMaterialTheme.colorScheme.bg_subtle
        borderColor = RemindMaterialTheme.colorScheme.bg_subtle
        borderWidth = 0.dp
    }

    return Modifier
        .aspectRatio(1f)
        .fillMaxWidth()
        .border(
            width = borderWidth,
            color = borderColor,
            shape = RoundedCornerShape(8.dp)
        )
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
        .then(modifier)
}

private fun setupImageCardRequest(context: Context, url: String) =
    ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .build()