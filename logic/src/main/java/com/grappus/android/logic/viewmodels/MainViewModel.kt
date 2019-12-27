package com.grappus.android.logic.viewmodels

import com.grappus.android.core.contracts.MainContract
import com.grappus.android.core.providers.SchedulersProvider
import javax.inject.Inject

/**
 * Created by cp@grappus.com on 20/12/2019.
 * Description - Main view model
 */

class MainViewModel @Inject constructor(schedulersProvider: SchedulersProvider) :
    MainContract.ViewModel(schedulersProvider)