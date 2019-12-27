package com.grappus.android.di

import com.grappus.android.fragments.SplashFragment

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - Fragment binder
 */

interface FragmentInjector {
    fun inject(fragment: SplashFragment)
}