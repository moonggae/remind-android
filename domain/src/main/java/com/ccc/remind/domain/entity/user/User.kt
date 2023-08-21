package com.ccc.remind.domain.entity.user

import org.json.JSONObject
import java.util.Base64
import java.util.UUID

data class User(
    val accessToken: String,
    val refreshToken: String,
    val displayName: String?,
    val logInType: LogInType
) {
    val uuid: UUID
        get() {
            val payload = accessToken.split(".")[1]
            val decodedJson = String(Base64.getDecoder().decode(payload))
            val id = JSONObject(decodedJson).get("id").toString()
            return UUID.fromString(id)
        }
}