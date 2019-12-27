package com.grappus.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.grappus.android.basemvi.BuildConfig
import com.grappus.android.providers.KeyStoreProviderImpl
import com.grappus.android.providers.StringProviderImpl
import com.grappus.android.api.dispatcher.CommunicationBusProvider
import com.grappus.android.api.dispatcher.CommunicationBusProviderImpl
import com.grappus.android.api.entities.BuildInfo
import com.grappus.android.api.interceptors.NetInterceptorProvider
import com.grappus.android.api.providers.KeyStoreProvider
import com.grappus.android.core.providers.StringProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - App components provider
 */

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideConnectivityManager(application: Application): ConnectivityManager {
        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun providePreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideBuildInfo(): BuildInfo {
        val buildInfo = BuildInfo()
        buildInfo.baseUrl = BuildConfig.BASE_URL
        return buildInfo
    }

    @Provides
    fun provideStringProvider(stringProvider: StringProviderImpl): StringProvider = stringProvider

    @Provides
    fun provideKeyStore(keyStore: KeyStoreProviderImpl): KeyStoreProvider = keyStore

    @Provides
    @Singleton
    fun provideCommunicationBus(): CommunicationBusProvider {
        return CommunicationBusProviderImpl()
    }

    @Provides
    @Singleton
    fun provideNetInterceptor(application: Application): NetInterceptorProvider {
        return NetInterceptorProvider()
    }
}