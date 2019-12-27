package com.grappus.android.di

import com.grappus.android.core.MainActivity
import com.grappus.android.core.AbstractActivity
import com.grappus.android.core.di.PerActivity
import com.grappus.android.logic.di.LogicModule
import dagger.BindsInstance
import dagger.Component

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - Activity binder
 */

@PerActivity
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class, LogicModule::class]
)
interface ActivityComponent : FragmentInjector {

    @Component.Builder
    interface Builder {

        fun build(): ActivityComponent

        fun appComponent(appComponent: AppComponent?): Builder

        @BindsInstance
        fun activity(abstractActivity: AbstractActivity<*, *, *>): Builder
    }

    fun inject(activity: MainActivity)
}