package com.ccc.remind.data.source.remote.model.common

data class HttpException(
    val statusCode: Int,
    val message: String?,
    val error: String?
)