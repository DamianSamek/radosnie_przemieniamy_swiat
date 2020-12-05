package com.wsiz.projekt_zespolowy.base

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel constructor() : ViewModel() {

    // Result is used in callbacks
    @SuppressLint("CheckResult")
    protected fun <T> Single<T>.io(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    // Result is used in callbacks
    @SuppressLint("CheckResult")
    protected fun Completable.io(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }
}