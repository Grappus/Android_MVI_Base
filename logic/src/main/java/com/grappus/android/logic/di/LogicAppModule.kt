package com.grappus.android.logic.di

import com.grappus.android.api.di.BaseApiModule
import com.grappus.android.core.util.ErrorHandler
import com.grappus.android.logic.util.ErrorHandlerImpl
import dagger.Module
import dagger.Provides

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description -
 */

@Module
class LogicAppModule : BaseApiModule() {

    @Provides
    fun providesErrorHandler(errorHandler: ErrorHandlerImpl): ErrorHandler = errorHandler
}