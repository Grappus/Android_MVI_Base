package com.grappus.core.core

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - Default error handler. Returns default messages for common errors such as no internet connection,
 * http error 400, 500, and so on.
 */

interface ErrorHandler {

    fun getErrorMessage(throwable: Throwable): String

    /**
     * Returns request error object mapped from the specified [throwable].
     */
    fun <T> getErrorData(throwable: Throwable, clazz: Class<T>): ErrorData<T>
}