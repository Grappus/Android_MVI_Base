package com.grappus.android.logic.service

import com.grappus.android.api.services.AuthApi
import com.grappus.android.api.cache.AuthStore
import com.grappus.android.api.dispatcher.CommunicationBusProviderImpl
import com.grappus.android.api.entities.AuthRequest
import com.grappus.android.api.entities.LoginResponse
import com.grappus.android.logic.di.BaseLogicTest
import com.grappus.android.logic.services.AuthService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - AuthServiceTest
 */

class AuthServiceTest : BaseLogicTest() {

    private lateinit var service: AuthService
    private val authApi: AuthApi = mock()

    private val authStore: AuthStore = mock()
    private val communicationBusProviderImpl = CommunicationBusProviderImpl()

    companion object {
        val TOKEN = "auth_token"
        val USER_ID = 12323L
        val EMAIL = "android@grappus.com"
        val PASSWORD = "food Is Important"
    }

    @Before
    fun SetUp() {
        service = AuthService(
            authApi,
            authStore,
            communicationBusProviderImpl
        )
    }

    @Test
    fun `login invokes appropriate api`() {
        //when
        whenever(authApi.login(AuthRequest(EMAIL, PASSWORD))).thenReturn(Single.just(LoginResponse(USER_ID, TOKEN)))
        val observer = service.login(EMAIL, PASSWORD).test()

        //then
        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue {
            it.userId == USER_ID && it.authToken == TOKEN
        }
    }
}