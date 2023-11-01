package com.ccc.remind.presentation.ui.component.pageComponent.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import kotlinx.coroutines.launch

@Composable
fun MindMemoCard(
    modifier: Modifier = Modifier,
    text: String,
    commentSize: Int = 0
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    var memoMaxLine by remember { mutableStateOf(1) }


    Column(
        modifier = modifier.then(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(RemindMaterialTheme.colorScheme.bg_muted)
                .fillMaxWidth()
                .padding(12.dp)
                .onSizeChanged {
                    scope.launch {
                        val height = with(density) { it.height.toDp() }
                        val textHeight = with(density) { 16.sp.toDp() }
                        val line = ((height - if (commentSize > 0) 21.dp else 0.dp) / textHeight).toInt()
                        if (line > 0) memoMaxLine = line
                    }
                }
        ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = RemindMaterialTheme.typography.regular_md,
            color = RemindMaterialTheme.colorScheme.fg_muted,
            overflow = TextOverflow.Ellipsis,
            maxLines = memoMaxLine
        )

        if (commentSize > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 21.dp)
            ) {
                Text(
                    text = "$commentSize",
                    style = RemindMaterialTheme.typography.regular_lg,
                    color = RemindMaterialTheme.colorScheme.fg_muted,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = stringResource(R.string.comment),
                    tint = RemindMaterialTheme.colorScheme.fg_subtle,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}