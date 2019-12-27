package com.grappus.android.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - ViewModel provider
 */

class ViewModelFactory<T> @Inject constructor(
    private val modelProvider: Provider<T>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = modelProvider.get() as T
}