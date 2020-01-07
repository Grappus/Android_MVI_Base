package com.grappus.android.logic.viewmodels

import com.grappus.android.core.contracts.SplashContract
import com.grappus.android.core.contracts.SplashContract.Constants
import com.grappus.android.core.contracts.SplashContract.Screen
import com.grappus.android.core.core.NavigationAction.DisplayScreen
import com.grappus.android.core.extensions.applyToMainSchedulers
import com.grappus.android.core.providers.SchedulersProvider
import com.grappus.android.logic.services.AuthService
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by cp@grappus.com on 24/12/2019.
 * Description - Launcher fragment
 */

class SplashViewModel @Inject constructor(
    schedulersProvider: SchedulersProvider,
    private val authService: AuthService
) : SplashContract.ViewModel(schedulersProvider) {

    init {
        awaitNavigation()
    }

    private fun awaitNavigation() {
        Completable.timer(
            Constants.SPLASH_DURATION,
            TimeUnit.SECONDS,
            schedulersProvider.computation()
        )
            .applyToMainSchedulers(schedulersProvider)
            .subscribe {
                onNavigationAction(
                    DisplayScreen(
                        if (authService.isLoggedIn) Screen.HOME else Screen.ON_BOARDING
                    )
                )
            }
            .bindToLifecycle()
    }
}