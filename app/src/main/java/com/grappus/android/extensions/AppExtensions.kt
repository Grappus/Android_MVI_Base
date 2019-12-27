package com.grappus.android.extensions

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - Extension functions for app components
 */

/**
 * Extension methods for [Context].
 */
fun Context.getLayoutInflater() =
    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showToast(messageResId: Int, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, messageResId, duration).show()
}

fun Context.getColorCompat(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)