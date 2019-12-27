package com.grappus.android.logic.di

import com.grappus.android.core.providers.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import javax.inject.Inject

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - BaseLogicTest to be inherited by all test classes
 */

open class BaseLogicTest {

    @Inject
    @JvmField
    var mockWebServer: MockWebServer? = null

    @Inject
    @JvmField
    var dispatcher: Dispatcher? = null

    protected var testSchedulers = TestScheduler()

    protected var schedulersProvider: SchedulersProvider = object : SchedulersProvider() {
        override fun mainThread(): Scheduler = testSchedulers

        override fun io(): Scheduler = testSchedulers

        override fun computation(): Scheduler = testSchedulers

        override fun newThread(): Scheduler = testSchedulers

        override fun trampoline(): Scheduler = testSchedulers

        override fun single(): Scheduler = testSchedulers
    }

    @After
    @Throws(Exception::class)
    fun TearDown() {
        mockWebServer?.apply {
            setDispatcher(dispatcher)
            shutdown()
        }
    }
}