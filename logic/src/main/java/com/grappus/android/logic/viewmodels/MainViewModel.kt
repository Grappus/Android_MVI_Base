package com.grappus.android.logic.viewmodels

import com.grappus.android.api.dispatcher.ResultEvent.ConnectivityChanged
import com.grappus.android.api.dispatcher.CommunicationBusProvider
import com.grappus.android.core.contracts.MainContract
import com.grappus.android.core.contracts.MainContract.ViewEvent.InternetConnectivityChanged
import com.grappus.android.core.providers.SchedulersProvider
import javax.inject.Inject

/**
 * Created by cp@grappus.com on 20/12/2019.
 * Description - Main view model
 */

class MainViewModel @Inject constructor(
    schedulersProvider: SchedulersProvider, val bus: CommunicationBusProvider
) : MainContract.ViewModel(schedulersProvider) {

    override fun onViewEvent(event: MainContract.ViewEvent) {
        when (event) {
            is InternetConnectivityChanged -> {
                bus.sendResult(ConnectivityChanged(event.internetAvailability))
            }
        }
    }
}