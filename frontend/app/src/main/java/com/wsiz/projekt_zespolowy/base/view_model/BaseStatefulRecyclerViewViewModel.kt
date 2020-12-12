package com.wsiz.projekt_zespolowy.base.view_model

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseStatefulRecyclerViewViewModel<State> : BaseViewModel<State>() {
    var recyclerView: RecyclerView? = null
    var adapterSavedPosition = 0


    fun setupRecyclerView(recyclerView: RecyclerView?) {
        this.recyclerView = recyclerView
    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
        adapterSavedPosition =
            (recyclerView?.layoutManager as LinearLayoutManager?)?.findLastCompletelyVisibleItemPosition()
                ?: 0
    }
}