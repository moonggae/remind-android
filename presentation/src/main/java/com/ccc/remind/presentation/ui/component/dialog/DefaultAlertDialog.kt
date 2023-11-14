package com.ccc.remind.presentation.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ccc.remind.presentation.ui.component.button.TextFillButton
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme


@Composable
fun DefaultAlertDialog(
    modifier: Modifier = Modifier,
    cancelLabelText: String = "취소",
    confirmLabelText: String = "확인",
    cancelLabelColor: Color = RemindMaterialTheme.colorScheme.fg_subtle,
    confirmLabelColor: Color = RemindMaterialTheme.colorScheme.warn_onAccent,
    contentText: String,
    onClickConfirmButton: () -> Unit,
    onClickCancelButton: () -> Unit,
    onDismissRequest: () -> Unit,
    buttonReverse: Boolean = false
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier.then(
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = RemindMaterialTheme.colorScheme.bg_muted)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = contentText,
                style = TextStyle(
                    color = RemindMaterialTheme.colorScheme.fg_default,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(vertical = 28.dp)
            )

            Divider(
                thickness = 0.5.dp,
                color = RemindMaterialTheme.colorScheme.fg_subtle
            )

            val cancelButton: @Composable () -> Unit = {
                TextFillButton(
                    text = cancelLabelText,
                    style = RemindMaterialTheme.typography.regular_lg,
                    contentColor = cancelLabelColor,
                    containerColor = RemindMaterialTheme.colorScheme.bg_muted,
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f, true),
                    onClick = onClickCancelButton
                )
            }

            val confirmButton: @Composable () -> Unit = {
                TextFillButton(
                    text = confirmLabelText,
                    style = RemindMaterialTheme.typography.regular_lg,
                    contentColor = confirmLabelColor,
                    containerColor = RemindMaterialTheme.colorScheme.bg_muted,
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f, true),
                    onClick = onClickConfirmButton
                )
            }

            Row {
                if(buttonReverse)
                    confirmButton()
                else
                    cancelButton()

                Divider(
                    color = RemindMaterialTheme.colorScheme.fg_subtle,
                    modifier = Modifier
                        .height(48.dp)
                        .width(0.5.dp)
                )

                if(buttonReverse)
                    cancelButton()
                else
                    confirmButton()
            }
        }
    }
}