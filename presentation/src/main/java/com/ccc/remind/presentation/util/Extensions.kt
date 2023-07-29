package com.ccc.remind.presentation.util

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