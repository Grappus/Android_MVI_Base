package com.grappus.android.logic.di

import dagger.Component
import javax.inject.Singleton

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description -
 */

@Singleton
@Component(modules = [(LogicTestModule::class)])
interface LogicTestComponent