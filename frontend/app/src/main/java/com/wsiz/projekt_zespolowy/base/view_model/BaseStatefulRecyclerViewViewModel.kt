package com.wsiz.projekt_zespolowy.base.view_model

abstract class BaseStatefulRecyclerViewViewModel<State> : BaseViewModel<State>() {
    var adapterSavedPosition = 0
}