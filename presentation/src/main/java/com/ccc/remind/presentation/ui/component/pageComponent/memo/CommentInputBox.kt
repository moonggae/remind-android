package com.ccc.remind.presentation.ui.component.pageComponent.memo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentInputBox(
    modifier: Modifier = Modifier,
    text: String,
    onChangedText: (String) -> Unit,
    onClickSendButton: () -> Unit
) {
    BasicTextField(
        onValueChange = onChangedText,
        value = text,
        textStyle = RemindMaterialTheme.typography.regular_md.copy(color = RemindMaterialTheme.colorScheme.fg_default),
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .heightIn(
                min = 32.dp,
                max = 160.dp
            ),
        cursorBrush = SolidColor(RemindMaterialTheme.colorScheme.fg_default)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier.then(
                Modifier
                    .height(IntrinsicSize.Min)
                    .background(RemindMaterialTheme.colorScheme.bg_default)
            )

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(RemindMaterialTheme.colorScheme.fg_subtle)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 10.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(RemindMaterialTheme.colorScheme.bg_subtle)
                        .padding(
                            vertical = 7.5.dp,
                            horizontal = 16.dp
                        )
                        .weight(1f)
                ) { it() }

                Column {
                    CompositionLocalProvider(
                        LocalMinimumInteractiveComponentEnforcement provides false
                    ) {
                        IconButton(
                            onClick = onClickSendButton,
                            modifier = Modifier
                                .size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_arrow_top_circle
                                ),
                                contentDescription = stringResource(R.string.send_button),
                                tint = RemindMaterialTheme.colorScheme.fg_subtle,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}