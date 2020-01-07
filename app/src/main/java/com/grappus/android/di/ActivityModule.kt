package com.grappus.android.di

import com.grappus.android.core.AbstractActivity
import com.grappus.android.core.di.PerActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - Activity components provider
 */

@Module
class ActivityModule {

    @Provides
    @PerActivity
    fun provideRxPermissions(activity: AbstractActivity<*, *, *>): RxPermissions {
        return RxPermissions(activity)
    }
}