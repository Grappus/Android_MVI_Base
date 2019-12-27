@file:JvmName("RxUtils")

package com.grappus.android.core.extensions

import com.grappus.android.core.providers.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3

/**
 * Created by nimish@grappus.com on 15/12/2019.
 * Description - Extensions functions to be used with Rx
 */

/*Zip multiple single value response calls*/
object KtSingle {

    fun <T1, T2> zip(a: Single<T1>, b: Single<T2>, subscribeOn: Scheduler): Single<Pair<T1, T2>> =
        Single.zip(
            a.subscribeOn(subscribeOn),
            b.subscribeOn(subscribeOn),
            PairFunction()
        )

    fun <T1, T2, T3> zip(
        a: Single<T1>, b: Single<T2>, c: Single<T3>, subscribeOn: Scheduler
    ): Single<Triple<T1, T2, T3>> =
        Single.zip(
            a.subscribeOn(subscribeOn),
            b.subscribeOn(subscribeOn),
            c.subscribeOn(subscribeOn),
            TripleFunction()
        )
}

class PairFunction<T1, T2> : BiFunction<T1, T2, Pair<T1, T2>> {
    override fun apply(t1: T1, t2: T2) = t1 to t2
}

class TripleFunction<T1, T2, T3> : Function3<T1, T2, T3, Triple<T1, T2, T3>> {
    override fun apply(t1: T1, t2: T2, t3: T3) = Triple(t1, t2, t3)
}

fun <T> Single<T>.applyToMainSchedulers(schedulersProvider: SchedulersProvider): Single<T> =
    subscribeOn(schedulersProvider.io()).observeOn(schedulersProvider.mainThread())

fun <T> Flowable<T>.applyToMainSchedulers(schedulersProvider: SchedulersProvider): Flowable<T> =
    subscribeOn(schedulersProvider.io()).observeOn(schedulersProvider.mainThread())

fun <T> Observable<T>.applyToMainSchedulers(schedulersProvider: SchedulersProvider): Observable<T> =
    subscribeOn(schedulersProvider.io()).observeOn(schedulersProvider.mainThread())

fun Completable.applyToMainSchedulers(schedulersProvider: SchedulersProvider): Completable =
    subscribeOn(schedulersProvider.io()).observeOn(schedulersProvider.mainThread())

/**
 * filterMap applies [Observable.filter] [Observable.map]
 * operators sequentially to return an implicitly casted observable
 * */
inline fun <reified T> Observable<*>.filterMap(): Observable<T> {
    return filter { it is T }
        .map { it as T }
}