package com.grappus.core.core.mvp

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.grappus.api.dispatcher.CommunicationBusProvider
import com.grappus.api.dispatcher.ResultEvent
import com.grappus.core.core.extensions.applyToMainSchedulers
import com.grappus.core.core.extensions.filterMap
import com.grappus.core.core.util.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.UnicastSubject
import timber.log.Timber

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - BaseViewModel to be inherited by all ViewModels
 */

abstract class BaseViewModel<VS, VE>(
    initialState: VS, val schedulersProvider: SchedulersProvider,
    communicationBusProvider: CommunicationBusProvider? = null
) : ViewModel(), LifecycleObserver {

    private val TAG = this.javaClass.name
    init {
        communicationBusProvider?.let { bus ->
            bus.getResultEventsObservable()
                .filterMap<ResultEvent.AuthError>()
                .subscribe({
                    onNavigationAction(NavigationAction.AuthError)
                },
                    { Timber.e(it) }
                )
        }
    }

    private val viewStatePublisher: BehaviorSubject<VS> = BehaviorSubject.createDefault(initialState)
    private var navigationActionPublisher: UnicastSubject<NavigationAction> = UnicastSubject.create()

    var viewEventStream: Observable<VE>? = null

    var currentState: VS = initialState
        @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
        internal set(value) {
            field = value
            value?.let {
                viewStatePublisher.onNext(value)
            }
        }

    private var isSubscribedToViewEvents = false

    open fun getViewStateObservable(): Observable<VS> = viewStatePublisher

    open fun getNavigationActionObservable(): Observable<NavigationAction> = navigationActionPublisher
        .doOnDispose {
            navigationActionPublisher = UnicastSubject.create()
        }

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * This disposable is for subscriptions that are
     * dependent on view's lifecycle or need to be
     * cleared during activity
     **/
    private var viewLifeCycleCompositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun onViewEvent(event: VE) {
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        compositeDisposable.clear()
    }

    protected fun onNavigationAction(action: NavigationAction) {
        navigationActionPublisher.onNext(action)
    }

    protected fun Disposable.bindToLifecycle() = apply {
        compositeDisposable.add(this)
    }

    private fun Disposable.bindViewToLifeCycle() = apply {
        viewLifeCycleCompositeDisposable.add(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onViewInActive() {
        viewLifeCycleCompositeDisposable.clear()
        isSubscribedToViewEvents = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun viewActive() {
        if (!isSubscribedToViewEvents) {
            subscribeToViewEvents()
            isSubscribedToViewEvents = true
        }
    }

    private fun subscribeToViewEvents() {
        viewEventStream?.let {
            isSubscribedToViewEvents = true
            it.applyToMainSchedulers(schedulersProvider)
                .subscribe({
                    onViewEvent(it)
                }, {
                    Timber.e(it)
                }).bindViewToLifeCycle()
        }
    }
}