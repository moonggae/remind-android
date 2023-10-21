package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest


@Composable
fun MindCardImage(
    modifier: Modifier = Modifier,
    url: String,
    selectType: MindCardSelectType? = null,
    radius: Dp = 8.dp,
    size: Dp? = null
) {

    val appliedModifier = setupMindCardImageModifier(
        modifier = modifier,
        selectType = selectType,
        radius = radius
    )

    Box(modifier = appliedModifier) {
        val context = LocalContext.current
        val request = buildCoilRequest(context, url)

        Box(
            contentAlignment = Alignment.Center,
            modifier =
            if (size == null) Modifier
                .fillMaxWidth()
                .padding(14.dp)
            else Modifier
                .size(size)
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
    selectType: MindCardSelectType? = null,
    radius: Dp = 8.dp
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
            shape = RoundedCornerShape(radius)
        )
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(radius)
        )
        .then(modifier)
}