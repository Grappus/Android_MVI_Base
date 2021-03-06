package com.grappus.android.core.providers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - Provides Different schedules at one place.
 */

@Singleton
open class SchedulersProvider @Inject constructor() {

    open fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

    open fun io(): Scheduler = Schedulers.io()

    open fun computation(): Scheduler = Schedulers.computation()

    open fun newThread(): Scheduler = Schedulers.newThread()

    open fun trampoline(): Scheduler = Schedulers.trampoline()

    open fun single(): Scheduler = Schedulers.single()

    fun from(executor: Executor): Scheduler = Schedulers.from(executor)
}