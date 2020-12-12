package com.wsiz.projekt_zespolowy.fragment.one_article

import androidx.lifecycle.MutableLiveData
import com.wsiz.projekt_zespolowy.base.view_model.BaseViewModel

class OneArticleViewModel : BaseViewModel<Unit>() {

    override val state: MutableLiveData<Unit>
        get() = MutableLiveData()
}