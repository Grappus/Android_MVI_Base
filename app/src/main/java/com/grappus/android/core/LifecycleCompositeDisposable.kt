package com.grappus.android.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - Manages view composite disposables as per lifecycle events
 */

class LifecycleCompositeDisposable : LifecycleObserver {

    private val disposableContainer: MutableMap<Lifecycle.Event, CompositeDisposable> =
        mutableMapOf()

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleEvent(source: LifecycleOwner, event: Lifecycle.Event) {
        disposableContainer[event]?.clear()
    }

    fun addDisposable(
        disposable: Disposable,
        disposeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
    ) {
        disposableContainer.getOrPut(disposeEvent, { CompositeDisposable() }).add(disposable)
    }
}