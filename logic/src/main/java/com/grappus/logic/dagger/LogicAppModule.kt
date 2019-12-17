package com.grappus.logic.dagger

import com.grappus.api.dagger.BaseApiModule
import com.grappus.core.core.ErrorHandler
import com.grappus.logic.util.ErrorHandlerImpl
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