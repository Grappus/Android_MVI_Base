package com.grappus.android.core.core

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - basic Navigation Actions
 */

sealed class NavigationAction {

    object AuthError : NavigationAction()

    data class DisplayScreen(val screenName: String, val data: Any? = null) : NavigationAction()

    data class DisplayError(val errorMessage: String, val data: Any? = null) : NavigationAction()

    data class Dialog(val screenName: String, val data: Any? = null)

    data class SnackBar(val message: String) : NavigationAction()

    data class Toast(val message: String) : NavigationAction()

    data class CloseScreen(val message: String? = null) : NavigationAction()
}