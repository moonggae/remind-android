package com.ccc.remind.presentation.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun <E> List<E>.toggle(value: E): List<E> {
    return if(contains(value)) {
        minus(value)
    } else {
        plus(value)
    }
}

fun <E> MutableList<E>.toggle(value: E): Boolean {
    return if(contains(value)) {
        add(value)
    } else {
        remove(value)
    }
}

fun ZonedDateTime.toFormatString(
    pattern: String,
    locale: Locale = Locale.KOREA
): String {
    val formatter = DateTimeFormatter
        .ofPattern(pattern)
        .withLocale(locale)
    return this.format(formatter)
}