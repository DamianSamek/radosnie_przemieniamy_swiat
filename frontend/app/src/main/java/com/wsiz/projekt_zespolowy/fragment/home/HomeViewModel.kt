package com.wsiz.projekt_zespolowy.fragment.home

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val dominikaParaText = MutableLiveData<String>()

    init {
        dominikaParaText.postValue("Dominika")
    }

    fun changeText() {
        Handler().postDelayed({
            dominikaParaText.postValue("Para")
        }, 3000)
    }
}