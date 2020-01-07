package com.grappus.android.logic.viewmodels

import com.grappus.android.core.contracts.SplashContract
import com.grappus.android.core.core.NavigationAction
import com.grappus.android.logic.di.BaseLogicTest
import com.grappus.android.logic.services.AuthService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Created by cp@grappus.com on 06/01/2020.
 * Description - Test implementation of [SplashViewModel]
 */

class SplashViewModelTest : BaseLogicTest() {

    private lateinit var viewEventsStream: PublishSubject<SplashContract.ViewEvent>

    private lateinit var viewModel: SplashViewModel

    private val authService: AuthService = mock()

    @Before
    fun setup() {
        viewEventsStream = PublishSubject.create()
        viewModel = SplashViewModel(schedulersProvider, authService)
        viewModel.viewEventStream = viewEventsStream
        viewModel.onViewInActive()
        testSchedulers.triggerActions()
    }

    @Test
    fun `test navigates to login on login event`() {

        //given
        whenever(authService.isLoggedIn).thenReturn(false)
        val navObserver = viewModel.getNavigationActionObservable().test()

        //when
        testSchedulers.triggerActions()
        testSchedulers.advanceTimeBy(SplashContract.Constants.SPLASH_DURATION, TimeUnit.SECONDS)

        //then
        navObserver.assertValue {
            it is NavigationAction.DisplayScreen && it.screenName == SplashContract.Screen.ON_BOARDING
        }
    }

    @Test
    fun `test navigates to home on login event`() {

        //given
        whenever(authService.isLoggedIn).thenReturn(true)
        val navObserver = viewModel.getNavigationActionObservable().test()

        //when
        testSchedulers.triggerActions()
        testSchedulers.advanceTimeBy(SplashContract.Constants.SPLASH_DURATION, TimeUnit.SECONDS)

        //then
        navObserver.assertValue {
            it is NavigationAction.DisplayScreen && it.screenName == SplashContract.Screen.HOME
        }
    }
}