package com.grappus.android.logic.services

import com.grappus.android.api.cache.AuthStore
import com.grappus.android.api.dispatcher.CommunicationBusProviderImpl
import com.grappus.android.api.dispatcher.ResultEvent.AuthError
import com.grappus.android.api.entities.AuthRequest
import com.grappus.android.api.entities.LoginResponse
import com.grappus.android.api.entities.User
import com.grappus.android.api.services.AuthApi
import com.grappus.android.logic.di.BaseLogicTest
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
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
    private val communicationBus = CommunicationBusProviderImpl()

    companion object {
        const val TOKEN = "auth_token"
        const val USER_ID = 12323L
        const val EMAIL = "android@grappus.com"
        const val PASSWORD = "food is important"
    }

    @Before
    fun setup() {
        service = AuthService(
            authApi,
            authStore,
            communicationBus
        )
    }

    @Test
    fun `login invokes appropriate api`() {

        //given
        whenever(authApi.login(AuthRequest(EMAIL, PASSWORD))).thenReturn(
            Single.just(
                LoginResponse(
                    USER_ID,
                    TOKEN
                )
            )
        )

        //when
        val observer = service.login(EMAIL, PASSWORD).test()

        //then
        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue {
            it.userId == USER_ID && it.authToken == TOKEN
        }
    }

    @Test
    fun `logout clears data`() {

        //when
        service.logout()

        //then
        verify(authStore).authToken = null
        verify(authStore).user = null
    }

    @Test
    fun `logged in status returns appropriate data`() {

        //given
        whenever(authStore.isLoggedIn).thenReturn(true)
        assert(service.isLoggedIn)

        //when
        whenever(authStore.isLoggedIn).thenReturn(false)

        //then
        assert(!service.isLoggedIn)
    }

    @Test
    fun `assert when auth error event logs out user`() {

        //when
        communicationBus.sendResult(AuthError)

        //then
        verify(authStore).authToken = null
        verify(authStore).user = null
    }

    @Test
    fun `returns proper user info from cache when available`() {

        //given
        val user = User(12345, firstName = "john", lastName = "smith", email = "john@smith.com")
        whenever(authStore.user).thenReturn(user)

        //then
        assert(service.user == user)
    }
}