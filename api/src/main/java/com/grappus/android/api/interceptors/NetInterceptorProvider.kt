package com.grappus.android.api.interceptors

import com.grappus.android.api.utils.HttpConstants.AUTHORIZATION_HEADER
import com.grappus.android.api.utils.HttpConstants.TOKEN_PLACEHOLDER
import com.grappus.android.api.cache.AuthStore
import com.grappus.android.api.dispatcher.CommunicationBusProvider
import com.grappus.android.api.dispatcher.ResultEvent
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by nimish@grappus.com on 10/12/2019.
 */

class NetInterceptorProvider(val interceptor: Interceptor? = null)

@Singleton
class AuthInterceptor @Inject constructor(
    private val bus: CommunicationBusProvider,
    private val authStore: AuthStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        authStore.authToken?.let {
            val builder = request.newBuilder()
            val header = request.headers().newBuilder().add(
                AUTHORIZATION_HEADER, String.format(TOKEN_PLACEHOLDER, it)
            ).build()
            request = builder.headers(header).build()
        }

        //handle unAuthorised response
        val result = chain.proceed(request)
        if (result.code() == HTTP_UNAUTHORIZED) {
            bus.sendResult(ResultEvent.AuthError)
        }

        return result
    }
}
