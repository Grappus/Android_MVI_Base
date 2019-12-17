package com.grappus.logic.service

import android.annotation.SuppressLint
import com.grappus.api.AuthApi
import com.grappus.api.cache.AuthStore
import com.grappus.api.dispatcher.CommunicationBusProvider
import com.grappus.api.dispatcher.ResultEvent
import com.grappus.api.entities.AuthRequest
import com.grappus.api.entities.LoginResponse
import com.grappus.api.entities.User
import com.grappus.core.core.extensions.filterMap
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - sample AuthService
 */

@Singleton
@SuppressLint("CheckResult")
class AuthService @Inject constructor(
    private val api: AuthApi,
    private val authStore: AuthStore,
    communicationBusProvider: CommunicationBusProvider
) {
    val isLoggedIn: Boolean
        get() = authStore.isLoggedIn

    val user: User?
        get() = authStore.user

    init {
        communicationBusProvider.getResultEventsObservable()
            .filterMap<ResultEvent.AuthError>()
            .subscribe {
                logout()
            }
    }

    fun logout() {
        authStore.authToken = null
        authStore.user = null
    }

    fun login(email: String, password: String): Single<LoginResponse> {
        return api.login(AuthRequest(email, password))
            .doOnSuccess {
                authStore.authToken = it.authToken
            }
    }
}