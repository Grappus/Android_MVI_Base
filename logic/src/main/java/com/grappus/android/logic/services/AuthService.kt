package com.grappus.android.logic.services

import android.annotation.SuppressLint
import com.grappus.android.api.services.AuthApi
import com.grappus.android.api.cache.AuthStore
import com.grappus.android.api.dispatcher.CommunicationBusProvider
import com.grappus.android.api.dispatcher.ResultEvent
import com.grappus.android.api.entities.AuthRequest
import com.grappus.android.api.entities.LoginResponse
import com.grappus.android.api.entities.User
import com.grappus.android.core.extensions.filterMap
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

    //sample login call
    fun login(email: String, password: String): Single<LoginResponse> {
        return api.login(AuthRequest(email, password))
            .doOnSuccess {
                authStore.authToken = it.authToken
            }
    }

    //logout user
    fun logout() {
        authStore.authToken = null
        authStore.user = null
    }
}