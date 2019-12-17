package com.grappus.logic.dagger

import com.grappus.core.core.ErrorHandler
import com.grappus.core.core.util.SchedulersProvider
import com.grappus.logic.service.AuthService

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description -
 */

interface LogicAppComponent {
    val schedulersProvider: SchedulersProvider
    val errorHandler: ErrorHandler
    val authService: AuthService
}