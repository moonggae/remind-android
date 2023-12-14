package com.ccc.remind.presentation.util.extensions

import com.ccc.remind.presentation.util.getTimestamp
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ZonedDateTime.toFormatString(
    pattern: String,
    locale: Locale = Locale.KOREA
): String {
    val formatter = DateTimeFormatter
        .ofPattern(pattern)
        .withLocale(locale)
    return this.format(formatter)
}

fun ZonedDateTime.toTimestamp(): String {
    return getTimestamp(this.toInstant().toEpochMilli())
}