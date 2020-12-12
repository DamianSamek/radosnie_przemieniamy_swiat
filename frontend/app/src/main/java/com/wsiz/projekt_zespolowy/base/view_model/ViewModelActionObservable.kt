package com.wsiz.projekt_zespolowy.base.view_model

import com.wsiz.projekt_zespolowy.utils.RxUtils
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

object ViewModelActionObservable {

    private val viewModelActionObservable = BehaviorSubject.create<ViewModelAction>()

    sealed class ViewModelAction {
        object UserPostsUpdated : ViewModelAction()
    }

    fun subscribe(onNext: (ViewModelAction) -> Unit): Disposable {
        val observer = RxUtils.disposableObserver(onNext)
        viewModelActionObservable.subscribe(observer)
        return observer
    }

    fun onNext(value: ViewModelAction) {
        viewModelActionObservable.onNext(value)
    }

    fun userPostsUpdated() {
        onNext(ViewModelAction.UserPostsUpdated)
    }
}