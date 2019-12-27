package com.grappus.android.core.util

import com.google.gson.annotations.SerializedName

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - Error Handling for whole app
 */

object NonHttpErrorCode {
    const val ERROR_CODE_TIME_OUT = -100
    const val ERROR_CODE_NO_INTERNET = -101
    const val ERROR_UNKNOWN = -102
}

//TODO Sample error response class to be changed later
data class ErrorData<T>(
    val payload: T?,
    val statusCode: Int,
    val message: String
)

data class ApiError(
    @SerializedName("message") val message: String
)