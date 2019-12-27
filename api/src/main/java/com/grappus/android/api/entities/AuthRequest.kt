package com.grappus.android.api.entities

import android.util.Base64
import com.google.gson.annotations.SerializedName
import com.grappus.android.api.utils.HttpConstants
import com.grappus.android.api.utils.HttpConstants.AUTH_BODY_SEPARATOR

/**
 * Created by nimish@grappus.com on 14/12/2019.
 * Description - Sample class to demonstrate simple BAse 64 encoding
 */

data class AuthRequest(val email: String, val password: String) {
    override fun toString(): String {
        val body = Base64.encodeToString(
            "$email$AUTH_BODY_SEPARATOR$password".toByteArray(),
            Base64.NO_WRAP
        )
        return String.format(HttpConstants.AUTH_BODY_PLACEHOLDER, body)
    }
}

data class LoginResponse(
    @SerializedName("personId") val userId: Long,
    @SerializedName("accessToken") val authToken: String
)