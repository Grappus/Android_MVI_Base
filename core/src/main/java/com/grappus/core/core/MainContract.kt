package com.grappus.core.core

import com.grappus.core.core.mvp.BaseViewModel
import com.grappus.core.core.util.SchedulersProvider

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - Interface for the MainActivity presenter
 */

interface MainContract {

    class ViewState
    sealed class ViewEvent

    abstract class ViewModel(schedulersProvider: SchedulersProvider) :
        BaseViewModel<ViewState, ViewEvent>(ViewState(), schedulersProvider)
}