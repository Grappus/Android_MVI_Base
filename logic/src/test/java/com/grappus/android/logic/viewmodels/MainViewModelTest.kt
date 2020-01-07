package com.grappus.android.logic.viewmodels

import com.grappus.android.logic.di.BaseLogicTest
import org.junit.Before
import org.junit.Test

/**
 * Created by cp@grappus.com on 06/01/2020.
 * Description -
 */

class MainViewModelTest : BaseLogicTest() {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(schedulersProvider)
    }

    @Test
    fun `main view model test`() {
        assert(::viewModel.isInitialized)
    }
}