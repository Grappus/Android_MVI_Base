package com.grappus.android.core

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import com.grappus.android.di.ActivityComponent
import com.grappus.android.di.ViewModelFactory
import com.grappus.android.core.core.BaseViewModel
import com.grappus.android.core.core.NavigationAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - Base activity with all the common components for activities & fragments
 */

abstract class AbstractActivity<VS, VE, VM : BaseViewModel<VS, VE>> : AppCompatActivity() {

    /*****************************************************************************************
     *
     * Composite Disposable
     */
    private val compositeDisposable = LifecycleCompositeDisposable()

    /**
     * Adds this [Disposable] to [compositeDisposable] to insure that it will be disposed when
     * the view is destroyed avoiding any memory leaks.
     */
    protected fun Disposable.bindToLifecycle(disposeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY) =
        apply {
            compositeDisposable.addDisposable(this, disposeEvent)
        }

    /*****************************************************************************************
     *
     * View Model
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>
    protected lateinit var viewModel: VM
        private set

    abstract fun getViewModelClass(): KClass<VM>

    protected open fun onViewModelReady() {}

    /*****************************************************************************************
     *
     * Toolbar
     */
    lateinit var toolbar: Toolbar

    /**
     * Called to check if this activity has a toolbar.
     * Note when overriding this method you must also override [.getToolbarId]
     * and return the id for the toolbar of this activity
     *
     * @return true if the activity has a toolbar
     */
    fun hasToolbar() = false

    /**
     * @return the id of the toolbar for this activity
     */
    val toolbarId: Int
        @IdRes get() = -1

    /*****************************************************************************************
     *
     * Lifecycle methods
     */
    @Suppress("UNCHECKED_CAST")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpActivity(savedInstanceState)

        if (hasToolbar()) {
            toolbar = findViewById(toolbarId)
            setSupportActionBar(toolbar)
        }

        lifecycle.addObserver(compositeDisposable)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass().java)
        viewModel.viewEventStream = viewEventsStream
        lifecycle.addObserver(viewModel)
        onViewModelReady()
        subscribeToViewStateUpdates()
    }

    override fun onResume() {
        subscribeToNavigationActions()
        super.onResume()
    }

    /*****************************************************************************************
     *
     * Custom Events
     *
     * Below method is invoked when a new view state is received from the view model.
     */
    protected open fun onViewStateUpdate(state: VS) {}

    /**
     * This method is invoked when a new navigation action is received from the view model.
     */
    protected open fun onNavigationAction(action: NavigationAction) {}

    protected val viewEventsStream: PublishSubject<VE> = PublishSubject.create()

    /**
     * This method is invoked when we wish to notify subscribers of view events
     */
    protected fun dispatchViewEvent(event: VE) {
        viewEventsStream.onNext(event)
    }

    /**
     * This method should do the dagger injection so that presenter is not null when calling
     * presenter#onActivityCreated
     * and also for setting contentView.
     *
     * @param savedInstanceState
     */
    protected abstract fun setUpActivity(savedInstanceState: Bundle?)

    /**
     * @return the injector object for this activity
     */
    abstract fun injector(): ActivityComponent

    private fun subscribeToViewStateUpdates() {
        viewModel.getViewStateObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onViewStateUpdate, Timber::e)
            .bindToLifecycle()
    }

    private var navigationActionDisposable: Disposable? = null

    private fun subscribeToNavigationActions() {
        // This can get called multiple times, so ensure we are only subscribing once at a time
        if (navigationActionDisposable?.isDisposed != false) {
            navigationActionDisposable = viewModel.getNavigationActionObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNavigationAction, Timber::e)
                .bindToLifecycle(Lifecycle.Event.ON_PAUSE)
        }
    }
}