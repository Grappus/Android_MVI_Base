package com.grappus.android.core.core

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.grappus.android.api.dispatcher.CommunicationBusProvider
import com.grappus.android.api.dispatcher.ResultEvent
import com.grappus.android.core.extensions.applyToMainSchedulers
import com.grappus.android.core.extensions.filterMap
import com.grappus.android.core.providers.SchedulersProvider
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
    initialState: VS,
    val schedulersProvider: SchedulersProvider,
    communicationBusProvider: CommunicationBusProvider? = null
) : ViewModel(), LifecycleObserver {

    init {
        communicationBusProvider?.let { bus ->
            bus.getResultEventsObservable()
                .filterMap<ResultEvent.AuthError>()
                .subscribe({
                    onNavigationAction(NavigationAction.AuthError)
                }, {
                    Timber.e(it)
                })
        }
    }

    /*****************************************************************************************
     *
     * ViewState
     * Responsible for emitting data to view components
     * Gets triggered from ViewModel by updating `currentState`
     */
    private val viewStatePublisher: BehaviorSubject<VS> =
        BehaviorSubject.createDefault(initialState)

    open fun getViewStateObservable(): Observable<VS> = viewStatePublisher

    var currentState: VS = initialState
        @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
        internal set(value) {
            field = value
            value?.let {
                viewStatePublisher.onNext(value)
            }
        }

    /*****************************************************************************************
     *
     * NavigationAction
     * Responsible for emitting actions for view components
     * Gets triggered from ViewModel by invoking `onNavigationAction` method
     */
    private var navigationActionPublisher: UnicastSubject<NavigationAction> =
        UnicastSubject.create()

    open fun getNavigationActionObservable(): Observable<NavigationAction> =
        navigationActionPublisher
            .doOnDispose {
                navigationActionPublisher = UnicastSubject.create()
            }

    protected fun onNavigationAction(action: NavigationAction) {
        navigationActionPublisher.onNext(action)
    }

    /*****************************************************************************************
     *
     * Composite Disposable - View
     * For subscriptions that are dependent on view's lifecycle or need to be cleared during activity
     * Cleared when `onDestroy` method from view's lifecycle is invoked
     */
    private var viewCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private fun Disposable.bindViewToLifeCycle() = apply {
        viewCompositeDisposable.add(this)
    }

    /*****************************************************************************************
     *
     * Composite Disposable - API
     * For api subscriptions that are dependent on viewModel's lifecycle
     * Cleared when `onCleared` method from viewModel's lifecycle is invoked
     */
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun Disposable.bindToLifecycle() = apply {
        compositeDisposable.add(this)
    }

    /**
     * Called when this ViewModel is no longer used and will be destroyed
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        compositeDisposable.clear()
    }

    /*****************************************************************************************
     *
     * ViewEvent
     * Handling the lifecycle events to subscribe or unsubscribe the view events
     * View events are dispatched from view to direct viewModel to perform some task/logical operation
     */

    var viewEventStream: Observable<VE>? = null
    private var isSubscribedToViewEvents = false

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onViewInActive() {
        viewCompositeDisposable.clear()
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
            it
                .applyToMainSchedulers(schedulersProvider)
                .subscribe({
                    onViewEvent(it)
                }, {
                    Timber.e(it)
                })
                .bindViewToLifeCycle()
        }
    }

    open fun onViewEvent(event: VE) {}
}