package com.grappus.android.core.contracts

import com.grappus.android.core.core.BaseViewModel
import com.grappus.android.core.providers.SchedulersProvider

/**
 * Created by cp@grappus.com on 24/12/2019.
 * Description -
 */

interface SplashContract {

    class ViewState

    sealed class ViewEvent

    abstract class ViewModel(schedulersProvider: SchedulersProvider) :
        BaseViewModel<ViewState, ViewEvent>(ViewState(), schedulersProvider)

    object Screen {
        const val HOME = "home"
        const val ON_BOARDING = "on_boarding"
    }

    object Constants {
        const val SPLASH_DURATION = 3L
    }
}