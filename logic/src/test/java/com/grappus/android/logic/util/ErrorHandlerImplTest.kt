package com.grappus.android.logic.util

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import com.grappus.android.api.entities.ErrorResponse
import com.grappus.android.core.extensions.fromJson
import com.grappus.android.core.providers.StringId
import com.grappus.android.core.providers.StringProvider
import com.grappus.android.core.util.ErrorData
import com.grappus.android.core.util.ErrorHandler
import com.grappus.android.core.util.NonHttpErrorCode
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by cp@grappus.com on 06/01/2020.
 * Description - Test implementation of [ErrorHandler]
 */

@RunWith(MockitoJUnitRunner::class)
class ErrorHandlerImplTest {

    @Mock
    lateinit var stringProvider: StringProvider
    @Mock
    lateinit var gson: Gson
    @Mock
    lateinit var connectivityManager: ConnectivityManager

    private lateinit var errorHandler: ErrorHandler

    private val mockErrorResponse: ErrorResponse =
        ErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, "", "")

    @Before
    fun setup() {
        errorHandler = ErrorHandlerImpl(connectivityManager, stringProvider, gson)
    }

    @Test
    fun testGetErrorMessageNoInternet() {

        //given
        val exception = UnknownHostException()
        val noInternetMessage = "No Internet!"

        whenever(stringProvider.getString(StringId.ERROR_NO_INTERNET)).thenReturn(noInternetMessage)

        //when
        val message = errorHandler.getErrorMessage(exception)

        //then
        Assert.assertEquals(noInternetMessage, message)
        Mockito.verify(stringProvider).getString(StringId.ERROR_NO_INTERNET)
    }

    @Test
    fun testGetErrorDataNoInternet() {

        //given
        val exception = UnknownHostException()
        val noInternetMessage = "No Internet!"

        whenever(stringProvider.getString(StringId.ERROR_NO_INTERNET)).thenReturn(noInternetMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(
                null,
                NonHttpErrorCode.ERROR_CODE_NO_INTERNET,
                noInternetMessage
            ), errorData
        )
    }

    @Test
    fun testGetErrorDataSocketTimeoutNoInternet() {

        //given
        val exception = SocketTimeoutException()
        val noInternetMessage = "No Internet!"

        whenever(connectivityManager.activeNetworkInfo).thenReturn(mock())
        whenever(stringProvider.getString(StringId.ERROR_NO_INTERNET)).thenReturn(noInternetMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(
                null,
                NonHttpErrorCode.ERROR_CODE_NO_INTERNET,
                noInternetMessage
            ), errorData
        )
    }

    @Test
    fun testGetErrorDataSocketTimeout() {

        //given
        val exception = SocketTimeoutException()
        val timeoutMessage = "Could not reach our servers."

        val networkInfo: NetworkInfo = mock { on { isConnected } doReturn true }
        whenever(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(stringProvider.getString(StringId.ERROR_TIME_OUT)).thenReturn(timeoutMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(null, NonHttpErrorCode.ERROR_CODE_TIME_OUT, timeoutMessage),
            errorData
        )
    }

    @Test
    fun testGetErrorMessageHttp400() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 400!"

        whenever(exception.code()).thenReturn(400)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_400)).thenReturn(httpErrorMessage)

        //when
        val message = errorHandler.getErrorMessage(exception)

        //then
        Assert.assertEquals(httpErrorMessage, message)
        Mockito.verify(stringProvider).getString(StringId.ERROR_NETWORK_400)
    }

    @Test
    fun testGetErrorDataHttp400() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 400!"

        whenever(exception.code()).thenReturn(400)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_400)).thenReturn(httpErrorMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(null, NonHttpErrorCode.ERROR_UNKNOWN, httpErrorMessage),
            errorData
        )
    }

    @Test
    fun testGetErrorMessageHttp408() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 408!"

        whenever(exception.code()).thenReturn(408)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_408)).thenReturn(httpErrorMessage)

        //when
        val message = errorHandler.getErrorMessage(exception)

        //then
        Assert.assertEquals(httpErrorMessage, message)
        Mockito.verify(stringProvider).getString(StringId.ERROR_NETWORK_408)
    }

    @Test
    fun testGetErrorDataHttp408() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 408!"

        whenever(exception.code()).thenReturn(408)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_408)).thenReturn(httpErrorMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(
                null,
                HttpURLConnection.HTTP_CLIENT_TIMEOUT,
                httpErrorMessage
            ), errorData
        )
    }

    @Test
    fun testGetErrorMessageHttp500() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 500!"

        whenever(exception.code()).thenReturn(500)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_500)).thenReturn(httpErrorMessage)

        //when
        val message = errorHandler.getErrorMessage(exception)

        //then
        Assert.assertEquals(httpErrorMessage, message)
        Mockito.verify(stringProvider).getString(StringId.ERROR_NETWORK_500)
    }

    @Test
    fun testGetErrorDataHttp500() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 500!"

        whenever(exception.code()).thenReturn(500)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_500)).thenReturn(httpErrorMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(
                null,
                HttpURLConnection.HTTP_INTERNAL_ERROR,
                httpErrorMessage
            ), errorData
        )
    }

    @Test
    fun testGetErrorMessageHttp501() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 501!"

        whenever(exception.code()).thenReturn(501)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_501)).thenReturn(httpErrorMessage)

        //when
        val message = errorHandler.getErrorMessage(exception)

        //then
        Assert.assertEquals(httpErrorMessage, message)
        Mockito.verify(stringProvider).getString(StringId.ERROR_NETWORK_501)
    }

    @Test
    fun testGetErrorDataHttp501() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 501!"

        whenever(exception.code()).thenReturn(501)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_501)).thenReturn(httpErrorMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(
                null,
                HttpURLConnection.HTTP_NOT_IMPLEMENTED,
                httpErrorMessage
            ), errorData
        )
    }

    @Test
    fun testGetErrorMessageHttp503() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 503!"

        whenever(exception.code()).thenReturn(503)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_503)).thenReturn(httpErrorMessage)

        //when
        val message = errorHandler.getErrorMessage(exception)

        //then
        Assert.assertEquals(httpErrorMessage, message)
        Mockito.verify(stringProvider).getString(StringId.ERROR_NETWORK_503)
    }

    @Test
    fun testGetErrorDataHttp503() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 503!"

        whenever(exception.code()).thenReturn(503)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_503)).thenReturn(httpErrorMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(null, HttpURLConnection.HTTP_UNAVAILABLE, httpErrorMessage),
            errorData
        )
    }

    @Test
    fun testGetErrorMessageHttp504() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 504!"

        whenever(exception.code()).thenReturn(504)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_504)).thenReturn(httpErrorMessage)

        //when
        val message = errorHandler.getErrorMessage(exception)

        //then
        Assert.assertEquals(httpErrorMessage, message)
        Mockito.verify(stringProvider).getString(StringId.ERROR_NETWORK_504)
    }

    @Test
    fun testGetErrorDataHttp504() {

        //given
        val exception: HttpException = mock()
        val httpErrorMessage = "HTTP 504!"

        whenever(exception.code()).thenReturn(504)
        whenever(stringProvider.getString(StringId.ERROR_NETWORK_504)).thenReturn(httpErrorMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(
                null,
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT,
                httpErrorMessage
            ), errorData
        )
    }

    @Test
    fun testGetErrorMessageHttpFromResponse() {

        //given
        val errorResponse: Response<String> = mock()
        val responseBody: ResponseBody = mock()
        val httpException: HttpException = mock()
        val mockErrorResponse: ErrorResponse = mock()

        whenever(httpException.code()).thenReturn(404)
        whenever(httpException.response()).thenReturn(errorResponse)
        whenever(errorResponse.errorBody()).thenReturn(responseBody)
        whenever(responseBody.string()).thenReturn("{}")

        val errorMessage = "Not Found!"
        whenever(mockErrorResponse.message).thenReturn(errorMessage)

        whenever(gson.fromJson<ErrorResponse>("{}")).thenReturn(mockErrorResponse)

        //when
        val message = errorHandler.getErrorMessage(httpException)

        //then
        Assert.assertEquals(errorMessage, message)
        Mockito.verify(gson).fromJson<ErrorResponse>("{}")
        Mockito.verify(mockErrorResponse).message
    }

    @Test
    fun testGetErrorDataHttpFromResponseNull() {

        //given
        val errorResponse: Response<String> = mock()
        val responseBody: ResponseBody = mock()
        val httpException: HttpException = mock()
        val mockErrorResponse: ErrorResponse = mock()

        whenever(httpException.code()).thenReturn(404)
        whenever(httpException.response()).thenReturn(errorResponse)
        whenever(errorResponse.errorBody()).thenReturn(responseBody)
        whenever(responseBody.string()).thenReturn("{}")

        val errorMessage = "Not Found!"
        whenever(mockErrorResponse.message).thenReturn(errorMessage)

        whenever(gson.fromJson<ErrorResponse>("{}")).thenReturn(mockErrorResponse)

        //when
        val errorData = errorHandler.getErrorData(httpException, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(ErrorData(null, httpException.code(), errorMessage), errorData)
    }

    @Test
    fun testGetErrorDataHttpFromResponseWithWrongJsonFormat() {

        //given
        val errorResponse: Response<String> = mock()
        val responseBody: ResponseBody = mock()
        val httpException: HttpException = mock()
        val mockErrorResponse: ErrorResponse = mock()

        whenever(httpException.code()).thenReturn(404)
        whenever(httpException.response()).thenReturn(errorResponse)
        whenever(errorResponse.errorBody()).thenReturn(responseBody)
        whenever(responseBody.string()).thenReturn("{}}")

        val errorMessage = "Not Found!"
        whenever(httpException.message()).thenReturn(errorMessage)

        //when
        val errorData = errorHandler.getErrorData(httpException, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(ErrorData(null, httpException.code(), errorMessage), errorData)
    }

    @Test
    fun testGetErrorDataHttpFromResponseWithNullErrorBody() {

        //given
        val errorResponse: Response<String> = mock()
        val httpException: HttpException = mock()
        val mockErrorResponse: ErrorResponse = mock()

        whenever(httpException.code()).thenReturn(404)
        whenever(httpException.response()).thenReturn(errorResponse)
        whenever(errorResponse.errorBody()).thenReturn(null)

        val errorMessage = "Not Found!"
        whenever(httpException.message()).thenReturn(errorMessage)

        //when
        val errorData = errorHandler.getErrorData(httpException, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(ErrorData(null, httpException.code(), errorMessage), errorData)
    }

    @Test
    fun testGetErrorDataHttpFromResponse() {

        //given
        val errorResponse: Response<ErrorResponse> = mock()
        val responseBody: ResponseBody = mock()
        val httpException: HttpException = mock()
        val mockErrorResponse = ErrorResponse(400, "", "")

        whenever(httpException.code()).thenReturn(400)
        whenever(httpException.response()).thenReturn(errorResponse)
        whenever(errorResponse.errorBody()).thenReturn(responseBody)
        whenever(responseBody.string()).thenReturn("{}")

        val errorMessage = "Not Found!"
        whenever(errorHandler.getErrorMessage(httpException)).thenReturn(errorMessage)

        whenever(gson.fromJson("{}", ErrorResponse::class.java)).thenReturn(mockErrorResponse)

        //when
        val errorData = errorHandler.getErrorData(httpException, ErrorResponse::class.java)

        //then
        Assert.assertEquals(
            ErrorData(
                ErrorResponse(400, "", ""),
                httpException.code(),
                errorMessage
            ), errorData
        )
    }

    @Test
    fun testGetErrorMessageUnknownException() {

        //given
        val exception = Exception()

        val errorMessage = "Unknown error!"
        whenever(stringProvider.getString(StringId.ERROR_UNKNOWN)).thenReturn(errorMessage)

        //when
        val message = errorHandler.getErrorMessage(exception)

        //then
        Assert.assertEquals(errorMessage, message)
        Mockito.verify(stringProvider).getString(StringId.ERROR_UNKNOWN)
    }

    @Test
    fun testGetErrorDataUnknownException() {

        //given
        val exception = Exception()

        val errorMessage = "Unknown error!"
        whenever(stringProvider.getString(StringId.ERROR_UNKNOWN)).thenReturn(errorMessage)

        //when
        val errorData = errorHandler.getErrorData(exception, mockErrorResponse.javaClass)

        //then
        Assert.assertEquals(
            ErrorData(null, NonHttpErrorCode.ERROR_UNKNOWN, errorMessage),
            errorData
        )
    }
}