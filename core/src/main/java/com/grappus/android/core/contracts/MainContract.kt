package com.grappus.android.core.contracts

import com.grappus.android.core.core.BaseViewModel
import com.grappus.android.core.providers.SchedulersProvider

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - Interface for the MainActivity presenter
 */

interface MainContract {

    class ViewState

    sealed class ViewEvent {
        data class InternetConnectivityChanged(val internetAvailability : Boolean) : ViewEvent()
    }

    abstract class ViewModel(schedulersProvider: SchedulersProvider) :
        BaseViewModel<ViewState, ViewEvent>(
            ViewState(), schedulersProvider)
}