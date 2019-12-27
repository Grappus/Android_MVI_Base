package com.grappus.android.fragments

import com.grappus.android.basemvi.R
import com.grappus.android.core.AbstractFragment
import com.grappus.android.basemvi.databinding.FragmentSplashBinding
import com.grappus.android.di.FragmentInjector
import com.grappus.android.providers.BindFragment
import com.grappus.android.core.contracts.SplashContract
import com.grappus.android.core.contracts.SplashContract.Screen
import com.grappus.android.core.core.NavigationAction
import com.grappus.android.core.core.NavigationAction.DisplayScreen

/**
 * Created by cp@grappus.com on 24/12/2019.
 * Description - Launcher fragment
 */

class SplashFragment :
    AbstractFragment<SplashContract.ViewState, SplashContract.ViewEvent, SplashContract.ViewModel>() {

    override fun getViewModelClass() = SplashContract.ViewModel::class

    override val binding: FragmentSplashBinding by BindFragment(R.layout.fragment_splash)

    override fun injectFragment(injector: FragmentInjector) {
        injector.inject(this)
    }

    override fun onNavigationAction(action: NavigationAction) {
        when (action) {
            is DisplayScreen -> {
                when (action.screenName) {
                    Screen.ON_BOARDING -> navigateTo(SplashFragmentDirections.moveToOnBoardingScreen())
                    Screen.HOME -> navigateTo(SplashFragmentDirections.moveToHomeScreen())
                    else -> super.onNavigationAction(action)
                }
            }
            else -> super.onNavigationAction(action)
        }
    }
}