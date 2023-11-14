package com.ccc.remind.presentation.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ccc.remind.presentation.ui.component.button.TextFillButton
import com.ccc.remind.presentation.ui.component.model.ButtonModel
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    titleText: String? = null,
    contentText: String,
    onDismiss: () -> Unit,
    buttons: List<ButtonModel>,
    buttonReverse: Boolean = false
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier.then(
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = RemindMaterialTheme.colorScheme.bg_muted)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            titleText?.let { text ->
                Text(
                    text = text,
                    style = RemindMaterialTheme.typography.bold_lg,
                    color = RemindMaterialTheme.colorScheme.fg_default,
                    modifier = Modifier.padding(
                        top = 28.dp,
                        bottom = 16.dp,
                        start = 40.dp,
                        end = 40.dp
                    )
                )
            }

            Text(
                text = contentText,
                style = TextStyle(
                    color = RemindMaterialTheme.colorScheme.fg_default,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(
                    top = if(titleText == null) 28.dp else 0.dp,
                    bottom = 28.dp,
                    start = 20.dp,
                    end = 20.dp
                )
            )

            Divider(
                thickness = 0.5.dp,
                color = RemindMaterialTheme.colorScheme.fg_subtle
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(0.5.dp)
            ) {
                val orderedButtons = if (buttonReverse) buttons.reversed() else buttons
                orderedButtons.forEach { button ->
                    TextFillButton(
                        text = stringResource(button.textResId),
                        style = RemindMaterialTheme.typography.regular_lg,
                        contentColor = button.priority.getColor(),
                        containerColor = RemindMaterialTheme.colorScheme.bg_muted,
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f, fill = true),
                        onClick = button.onClick
                    )

                    if (button != buttons.last()) {
                        Divider(
                            color = RemindMaterialTheme.colorScheme.fg_subtle,
                            modifier = Modifier
                                .height(48.dp)
                                .width(0.5.dp)
                        )
                    }
                }
            }
        }
    }
}