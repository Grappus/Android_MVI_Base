package com.grappus.android.di

import androidx.databinding.DataBindingComponent
import com.grappus.android.binding.AppBindingAdapters
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cp@grappus.com on 20/12/2019.
 * Description - Binding component for custom binding adapters
 */

@Singleton
class BindingComponent @Inject constructor(
    private val appBindingAdapters: AppBindingAdapters
) : DataBindingComponent {
    override fun getAppBindingAdapters() = appBindingAdapters
}