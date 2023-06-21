package com.ccc.remind.presentation.util

class ValidationUtil {
    companion object {
        fun displayNameValidate(text: String) : Boolean {
            val regex = Regex("^[가-힣A-Za-z0-9]{2,10}\$")
            return text.matches(regex)
        }
    }
}