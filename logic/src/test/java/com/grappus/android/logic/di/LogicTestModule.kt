package com.grappus.android.logic.di

import com.grappus.android.api.di.LocalApiModule
import com.grappus.android.api.entities.BuildInfo
import com.grappus.android.api.interceptors.NetInterceptorProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description -
 */

@Module
class LogicTestModule : LocalApiModule() {

    //Reduce read time for testing purposes
    override fun addTestSpecificConfig(builder: OkHttpClient.Builder) {
        builder.readTimeout(1, TimeUnit.SECONDS)
    }

    @Provides
    @Singleton
    fun provideInterceptor(): NetInterceptorProvider = NetInterceptorProvider()

    @Provides
    @Singleton
    fun provideBuildInfo(): BuildInfo = BuildInfo()
}