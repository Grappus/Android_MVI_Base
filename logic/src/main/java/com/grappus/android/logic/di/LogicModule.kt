package com.grappus.android.logic.di

import com.grappus.android.core.contracts.MainContract
import com.grappus.android.core.contracts.SplashContract
import com.grappus.android.logic.viewmodels.MainViewModel
import com.grappus.android.logic.viewmodels.SplashViewModel
import dagger.Binds
import dagger.Module

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description -
 */

@Module
abstract class LogicModule {

    @Binds
    abstract fun provideMainViewModel(viewModel: MainViewModel): MainContract.ViewModel

    @Binds
    abstract fun provideSplashViewModel(viewModel: SplashViewModel): SplashContract.ViewModel
}