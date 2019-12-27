package com.grappus.android.logic.di

import com.grappus.android.core.providers.SchedulersProvider
import com.grappus.android.core.util.ErrorHandler
import com.grappus.android.logic.services.AuthService

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - Logic components binder
 */

interface LogicAppComponent {
    val schedulersProvider: SchedulersProvider
    val errorHandler: ErrorHandler
    val authService: AuthService
}