package com.grappus.android.api.dispatcher

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by nimish@grappus.com on 10/12/2019.
 */

sealed class ResultEvent {
    object AuthError : ResultEvent()
    data class AuthChanged(val isLoggedIn: Boolean) : ResultEvent()
}

interface CommunicationBusProvider {
    fun getResultEventsObservable(): Observable<ResultEvent>
    fun sendResult(resultEvent: ResultEvent)
}

class CommunicationBusProviderImpl : CommunicationBusProvider {
    private val resultsEventsStream = PublishSubject.create<ResultEvent>()

    override fun getResultEventsObservable(): Observable<ResultEvent> = resultsEventsStream

    override fun sendResult(resultEvent: ResultEvent) {
        resultsEventsStream.onNext(resultEvent)
    }
}