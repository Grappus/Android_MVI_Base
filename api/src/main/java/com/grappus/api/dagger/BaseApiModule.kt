package com.grappus.api.dagger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.grappus.api.AuthApi
import com.grappus.api.BuildConfig
import com.grappus.api.convertors.EnumRetrofitConverterFactory
import com.grappus.api.entities.BuildInfo
import com.grappus.api.extensions.create
import com.grappus.api.interceptors.AuthInterceptor
import com.grappus.api.interceptors.NetInterceptorProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by nimish@grappus.com on 10/12/2019.
 */

@Module
open class BaseApiModule {

    val loggingInterceptor: HttpLoggingInterceptor
        @Provides
        @Singleton
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return loggingInterceptor
        }

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        netInterceptorProvider: NetInterceptorProvider,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

        if (netInterceptorProvider.interceptor != null) {
            builder.addInterceptor(netInterceptorProvider.interceptor)
        }

        builder.addInterceptor(authInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        enumRetrofitConverterFactory: EnumRetrofitConverterFactory,
        buildInfo: BuildInfo
    ): Retrofit {
        val apiUrl = (if (mockApiUrl() == null) buildInfo.apiUrl else mockApiUrl()) as String

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(enumRetrofitConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create()

    protected open fun mockApiUrl(): String? = null

    protected open fun addTestSpecificConfig(builder: OkHttpClient.Builder) {
    }
}