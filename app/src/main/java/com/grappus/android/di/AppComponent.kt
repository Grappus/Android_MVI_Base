package com.grappus.android.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.grappus.android.core.BaseApplication
import com.grappus.android.api.dispatcher.CommunicationBusProvider
import com.grappus.android.api.providers.KeyStoreProvider
import com.grappus.android.core.providers.StringProvider
import com.grappus.android.logic.di.LogicAppComponent
import com.grappus.android.logic.di.LogicAppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - App components binder
 */

@Singleton
@Component(
    modules = [AppModule::class, LogicAppModule::class]
)
interface AppComponent : LogicAppComponent {

    val application: Application

    val connectivityManager: ConnectivityManager

    val bindingComponent: BindingComponent

    val stringProvider: StringProvider

    val keyStoreProvider: KeyStoreProvider

    val communicationBusProvider: CommunicationBusProvider

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder
    }

    fun inject(application: BaseApplication)
}