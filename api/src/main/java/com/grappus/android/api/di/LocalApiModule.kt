package com.grappus.android.api.di

import com.grappus.android.api.dispatcher.LocalResponseDispatcher
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

/**
 * Created by nimish@grappus.com on 16/12/2019.
 */
/*
* Contains the `MockWebServer` logic and is extending by the `local` build variant module and is
* also extended by `test` module. LocalApiModule is responsible for providing the appropriate URL
* on which MockWebServer runs. It also initializes the `LocalResponseDispatcher` which selects the
* appropriate `json` file from the `local-resources` module.
*/

@Module
open class LocalApiModule : BaseApiModule() {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val localResponseDispatcher: LocalResponseDispatcher = LocalResponseDispatcher()

    val testDispatcher: Dispatcher
        @Provides
        @Singleton
        get() = localResponseDispatcher

    init {
        mockWebServer.setDispatcher(localResponseDispatcher)
    }

    override fun mockApiUrl(): String = mockWebServer.url("/").toString()

    override fun addTestSpecificConfig(builder: OkHttpClient.Builder) {}

    @Provides
    @Singleton
    fun provideDefaultMockServer(): MockWebServer = mockWebServer
}