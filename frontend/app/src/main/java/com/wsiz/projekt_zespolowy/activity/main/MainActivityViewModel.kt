package com.wsiz.projekt_zespolowy.activity.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.wsiz.projekt_zespolowy.base.fragment.view_model.BaseViewModel

class MainActivityViewModel @ViewModelInject constructor() : BaseViewModel<Unit>() {

    override val state: MutableLiveData<Unit>
        get() = MutableLiveData()
}