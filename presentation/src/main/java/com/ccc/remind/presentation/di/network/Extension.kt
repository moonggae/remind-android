package com.ccc.remind.presentation.di.network

import okhttp3.Request

fun Request.putTokenHeader(accessToken: String): Request {
    return this.newBuilder().addHeader("authorization", "Bearer $accessToken").build()
}