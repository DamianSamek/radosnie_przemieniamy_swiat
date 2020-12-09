package com.wsiz.projekt_zespolowy.base.fragment.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel<State> : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    abstract val state: MutableLiveData<State>

    protected fun <T> Single<T>.io(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
        compositeDisposable.add(
            this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
        )
    }

    protected fun Completable.io(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        compositeDisposable.add(
            this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
        )
    }

    fun onViewDestroyed() {
        state.postValue(null)
        compositeDisposable.dispose()
    }
}