package com.ccc.remind.presentation.util.retrofit

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.Response
import retrofit2.HttpException
import java.nio.charset.Charset

fun Response.isTokenInvalid(): Boolean {
    val jwtExpiredMessage = "jwt expired"

    val responseSource = this.body?.source()
    responseSource?.request(Long.MAX_VALUE)
    val responseBody: String? = responseSource?.buffer?.clone()?.readString(Charset.forName("UTF-8"))
    if (responseBody != null) {
        return try {
            val exception = Gson().fromJson(responseBody, HttpException::class.java)
            exception.message() == jwtExpiredMessage
        } catch (e: JsonSyntaxException) {
            false
        }
    }
    return false
}