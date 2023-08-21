package com.ccc.remind.presentation.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ccc.remind.R

private val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard)
)


val ts_primary_button_text = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 20.sp,
    fontWeight = FontWeight(700),
)
val ts_bold_md = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    fontWeight = FontWeight(700)
)
val ts_bold_lg = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    fontWeight = FontWeight(700)
)
val ts_bold_xl = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 20.sp,
    fontWeight = FontWeight(700)
)
val ts_bold_xxl = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 24.sp,
    fontWeight = FontWeight(700)
)
val ts_regular_sm = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 12.sp,
    lineHeight = 16.8.sp,
    fontWeight = FontWeight(400),
)
val ts_regular_md = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 14.sp,
    lineHeight = 19.6.sp,
    fontWeight = FontWeight(400),
)
val ts_regular_lg = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 16.sp,
    lineHeight = 22.4.sp,
    fontWeight = FontWeight(400)
)
val ts_regular_xl = TextStyle(
    fontFamily = pretendardFontFamily,
    fontSize = 20.sp,
    fontWeight = FontWeight(400)
)

class RemindTypography(
    val primary_button_text: TextStyle,
    val bold_md: TextStyle,
    val bold_lg: TextStyle,
    val bold_xl: TextStyle,
    val bold_xxl: TextStyle,
    val regular_sm: TextStyle,
    val regular_md: TextStyle,
    val regular_lg: TextStyle,
    val regular_xl: TextStyle,
) {

}


object RemindTypographyTokens {
    val primary_button_text = ts_primary_button_text
    val bold_md = ts_bold_md
    val bold_lg = ts_bold_lg
    val bold_xl = ts_bold_xl
    val bold_xxl = ts_bold_xxl
    val regular_sm = ts_regular_sm
    val regular_md = ts_regular_md
    val regular_lg = ts_regular_lg
    val regular_xl = ts_regular_xl
}

fun remindTypography(
    primary_button_text: TextStyle = RemindTypographyTokens.primary_button_text,
    bold_md: TextStyle = RemindTypographyTokens.bold_md,
    bold_lg: TextStyle = RemindTypographyTokens.bold_lg,
    bold_xl: TextStyle = RemindTypographyTokens.bold_xl,
    bold_xxl: TextStyle = RemindTypographyTokens.bold_xxl,
    regular_sm: TextStyle = RemindTypographyTokens.regular_sm,
    regular_md: TextStyle = RemindTypographyTokens.regular_md,
    regular_lg: TextStyle = RemindTypographyTokens.regular_lg,
    regular_xl: TextStyle = RemindTypographyTokens.regular_xl,
): RemindTypography = RemindTypography(
    primary_button_text,
    bold_md,
    bold_lg,
    bold_xl,
    bold_xxl,
    regular_sm,
    regular_md,
    regular_lg,
    regular_xl
)

