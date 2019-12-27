package com.grappus.android.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment

/**
 * Created by cp@grappus.com on 24/12/2019.
 * Description - Extension functions for Fragments
 */

/**
 * Extension method used to display a [Toast] message to the user.
 */
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    context?.showToast(message, duration)
}

fun Fragment.showToast(messageResId: Int, duration: Int = Toast.LENGTH_LONG) {
    context?.showToast(messageResId, duration)
}

/**
 * Extension method to show/hide keyboard for [Fragment].
 */
fun Fragment.hideSoftKeyboard() {
    activity?.apply {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService<InputMethodManager>()
            inputMethodManager?.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}

fun Fragment.showSoftKeyboard() {
    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}
