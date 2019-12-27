package com.grappus.android.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.grappus.android.R
import com.grappus.android.core.core.BaseViewModel
import com.grappus.android.core.core.NavigationAction
import com.grappus.android.di.FragmentInjector
import com.grappus.android.di.ViewModelFactory
import com.grappus.android.extensions.hideSoftKeyboard
import com.grappus.android.extensions.showErrorSnack
import com.grappus.android.extensions.showSnack
import com.grappus.android.extensions.showSoftKeyboard
import com.grappus.android.extensions.showToast
import com.grappus.android.listeners.ToolbarListener
import com.grappus.android.providers.TextProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.UnicastSubject
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by cp@grappus.com on 20/12/2019.
 * Description - Base fragment with all the common components
 */

abstract class AbstractFragment<VS, VE, VM : BaseViewModel<VS, VE>> : Fragment(), ToolbarListener {

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

    /**
     * @return the presenter for this fragment
     */
    protected lateinit var viewModel: VM
        private set

    /**
     * Returns the class for the view model associated to this fragment.
     */
    abstract fun getViewModelClass(): KClass<VM>

    protected open fun onViewModelReady() {}

    /*****************************************************************************************
     *
     * View Events
     */
    private val viewEventsStream: UnicastSubject<VE> = UnicastSubject.create()

    /**
     * This method is invoked when we wish to notify subscribers of view events
     */
    protected fun dispatchViewEvent(event: VE) {
        viewEventsStream.onNext(event)
    }

    /*****************************************************************************************
     *
     * Navigation Actions
     */
    private var navigationActionDisposable: Disposable? = null

    private fun subscribeToNavigationActions() {
        if (navigationActionDisposable?.isDisposed != false) {
            navigationActionDisposable = viewModel.getNavigationActionObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNavigationAction, Timber::e)
                .bindToLifecycle(Lifecycle.Event.ON_PAUSE)
        }
    }

    /**
     * This method is invoked when a new navigation action is received from the view model.
     * Don't forget to invoke super method in order to handle below actions by default
     */
    protected open fun onNavigationAction(action: NavigationAction) {
        when (action) {
            is NavigationAction.Toast -> showToast(action.message)
            is NavigationAction.SnackBar -> rootView.showSnack(
                message = TextProvider.Str(action.message)
            )
            is NavigationAction.DisplayError -> rootView.showErrorSnack(
                message = TextProvider.Str(
                    action.errorMessage
                )
            )
            is NavigationAction.AuthError -> {
                requireContext().showToast(R.string.error_session_expired)
                findNavController().navigate(R.id.move_to_auth_screen)
            }
            is NavigationAction.CloseScreen -> {
                action.message?.let { showToast(it) }
                onBackPressed()
            }
        }
    }

    protected fun navigateTo(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    /*****************************************************************************************
     *
     * View, View State, View Lifecycle & Custom Methods
     */
    protected open val rootView: View
        get() = view ?: throw IllegalStateException("View not inflated yet")
    protected open val focusableView: View? = null

    protected abstract val binding: ViewDataBinding

    private fun subscribeToViewStateUpdates() {
        viewModel.getViewStateObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onViewStateUpdate, Timber::e)
            .bindToLifecycle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = binding.root
        setupView()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectFragment((activity as AbstractActivity<*, *, *>).injector())
    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewLifecycleOwner.lifecycle.addObserver(compositeDisposable)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass().java)
        viewModel.viewEventStream = viewEventsStream
        lifecycle.addObserver(viewModel)

        onViewModelReady()
        subscribeToViewStateUpdates()
    }

    override fun onResume() {
        subscribeToNavigationActions()
        super.onResume()
        focusableView?.let {
            it.requestFocus()
            it.post { showSoftKeyboard() }
        }
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard()
    }

    override fun onBackPressed() {
        findNavController().navigateUp()
    }

    /**
     * Called when this fragment parent activity is created to
     * inject the current fragment.
     *
     * @param injector the injector object to use to inject the fragment
     */
    protected abstract fun injectFragment(injector: FragmentInjector)

    /**
     * Called when the layout for this fragment has been inflated to populate it with
     * the current fragment data
     */
    protected open fun setupView() {}

    /**
     * This method is invoked when a new view state is received from the view model.
     */
    protected open fun onViewStateUpdate(state: VS) {}

    protected fun <T : LifecycleObserver> T.observeLifecycle(): T {
        lifecycle.addObserver(this)
        return this
    }
}