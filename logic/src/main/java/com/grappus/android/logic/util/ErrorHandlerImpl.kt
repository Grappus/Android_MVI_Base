package com.grappus.android.logic.util

import android.net.ConnectivityManager
import com.google.gson.Gson
import com.grappus.android.api.entities.ErrorResponse
import com.grappus.android.core.extensions.fromJson
import com.grappus.android.core.providers.StringId
import com.grappus.android.core.providers.StringProvider
import com.grappus.android.core.util.ErrorData
import com.grappus.android.core.util.ErrorHandler
import com.grappus.android.core.util.NonHttpErrorCode
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_BAD_METHOD
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - Implementation of [ErrorHandler]
 */

class ErrorHandlerImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val stringProvider: StringProvider,
    private val gson: Gson
) : ErrorHandler {

    //TODO Change deprecated implementation
    private val isInternetAvailable: Boolean
        get() = connectivityManager.activeNetworkInfo?.isConnected ?: false

    override fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> getString(StringId.ERROR_NO_INTERNET)
            is HttpException -> when (throwable.code()) {
                HttpURLConnection.HTTP_BAD_REQUEST -> getString(StringId.ERROR_NETWORK_400)
                HttpURLConnection.HTTP_CLIENT_TIMEOUT -> getString(StringId.ERROR_NETWORK_408)
                HttpURLConnection.HTTP_INTERNAL_ERROR -> getString(StringId.ERROR_NETWORK_500)
                HttpURLConnection.HTTP_NOT_IMPLEMENTED -> getString(StringId.ERROR_NETWORK_501)
                HttpURLConnection.HTTP_UNAVAILABLE -> getString(StringId.ERROR_NETWORK_503)
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> getString(StringId.ERROR_NETWORK_504)
                else -> throwable.getErrorMessage()
            }
            else -> getString(StringId.ERROR_UNKNOWN)
        }
    }

    override fun <T> getErrorData(throwable: Throwable, clazz: Class<T>): ErrorData<T> =
        when (throwable) {
            is HttpException -> when (throwable.code()) {
                HTTP_BAD_REQUEST, HTTP_BAD_METHOD -> throwable.getErrorData(clazz)
                else -> ErrorData<T>(
                    payload = null,
                    statusCode = throwable.code(),
                    message = getString(StringId.ERROR_NO_INTERNET)
                )
            }
            is UnknownHostException -> ErrorData(
                payload = null,
                statusCode = NonHttpErrorCode.ERROR_CODE_NO_INTERNET,
                message = getString(StringId.ERROR_NO_INTERNET)
            )
            is SocketTimeoutException -> {
                val (statusCode, message) = if (isInternetAvailable) {
                    Pair(NonHttpErrorCode.ERROR_CODE_TIME_OUT, getString(StringId.ERROR_TIME_OUT))
                } else {
                    Pair(
                        NonHttpErrorCode.ERROR_CODE_NO_INTERNET,
                        getString(StringId.ERROR_NO_INTERNET)
                    )
                }
                ErrorData(
                    payload = null,
                    statusCode = statusCode,
                    message = message
                )
            }
            else -> ErrorData(
                payload = null,
                statusCode = NonHttpErrorCode.ERROR_UNKNOWN,
                message = getString(StringId.ERROR_UNKNOWN)
            )
        }

    /**
     * Retrieve string with [stringId] from application resources.
     */
    private fun getString(stringId: Int) = stringProvider.getString(stringId)

    private fun HttpException.getErrorMessage(): String {
        val errorBody = response().errorBody()?.string()

        return if (errorBody != null) {
            try {
                val error: ErrorResponse = gson.fromJson(errorBody)
                error.message
            } catch (e: Exception) {
                message()
            }
        } else {
            message()
        }
    }

    /**
     * Parse error body and retrieve returned error object from server.
     */
    private fun <T> HttpException.getErrorData(clazz: Class<T>): ErrorData<T> {
        val errorBody = response().errorBody()?.string()
        return if (errorBody != null) {
            try {
                val errorContent = gson.fromJson(errorBody, clazz)
                ErrorData(
                    payload = errorContent,
                    statusCode = this.code(),
                    message = getErrorMessage(this)
                )
            } catch (e: Exception) {
                throw e
            }
        } else {
            ErrorData(
                payload = null,
                statusCode = NonHttpErrorCode.ERROR_UNKNOWN,
                message = getErrorMessage(this)
            )
        }
    }
}