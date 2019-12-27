package com.grappus.android.core

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.grappus.android.di.AppComponent
import com.grappus.android.di.DaggerAppComponent

/**
 * Created by cp@grappus.com on 20/12/2019.
 * Description -
 */

class BaseApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        //Dagger initialization
        component.inject(this)
        DataBindingUtil.setDefaultComponent(component.bindingComponent)
    }
}