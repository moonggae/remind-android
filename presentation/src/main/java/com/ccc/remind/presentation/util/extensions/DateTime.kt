package com.ccc.remind.presentation.util.extensions

import com.ccc.remind.presentation.util.getTimestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ZonedDateTime.toFormatString(
    pattern: String,
    locale: Locale = Locale.getDefault()
): String {
    val formatter = DateTimeFormatter
        .ofPattern(pattern)
        .withLocale(locale)
    return this.format(formatter)
}

fun ZonedDateTime.toTimestamp(): String {
    return getTimestamp(this.toInstant().toEpochMilli())
}

fun LocalDate.toZonedDateTime(): ZonedDateTime =
    ZonedDateTime.of(this.atStartOfDay(), ZoneId.systemDefault())