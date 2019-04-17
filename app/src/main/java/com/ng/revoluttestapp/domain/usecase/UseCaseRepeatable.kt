package com.ng.revoluttestapp.domain.usecase

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

abstract class UseCaseRepeatable<T>(private val transformer: Transformer<T>) {

    abstract fun createObservable(data: Map<String, Any>? = null): Observable<T>

    fun observable(withData: Map<String, Any>? = null): Observable<T> {
        return createObservable(withData)
            .compose(transformer)
            .repeatWhen { observable -> observable.delay(1, TimeUnit.SECONDS) }
    }
}