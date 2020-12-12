package com.wsiz.projekt_zespolowy.utils

import io.reactivex.observers.DisposableObserver

object RxUtils {

    fun <T> disposableObserver(onNext: (T) -> Unit): DisposableObserver<T> {
        return object : DisposableObserver<T>() {
            override fun onNext(t: T) {
                onNext(t)
            }
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }
    }
}