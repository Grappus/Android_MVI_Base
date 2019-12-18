package com.grappus.core.core

import com.google.gson.annotations.SerializedName

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - Error Handling for whole app
 */

//
object NonHttpErrorCode {
    const val ERROR_CODE_TIME_OUT = -100
    const val ERROR_CODE_NO_INTERNET = -101
    const val ERROR_UNKNOWN = -102
    const val ERROR_VERIFY_EMAIL = 601
}

//sample error response class to be changed later
data class ErrorData<T>(
    val payload: T?,
    val statusCode: Int,
    val altMessage: String
)

data class ApiError(
    @SerializedName("messege") val message: String
)