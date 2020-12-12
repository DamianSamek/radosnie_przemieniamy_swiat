package com.wsiz.projekt_zespolowy.base.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.base.view_model.BaseStatefulRecyclerViewViewModel
import com.wsiz.projekt_zespolowy.base.view_model.BaseViewModel

abstract class BaseStatefulRecyclerViewFragment<ViewModel : BaseStatefulRecyclerViewViewModel<*>> :
    BaseFragment<ViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRecyclerView()?.scrollToPosition(viewModel.adapterSavedPosition)
    }

    abstract fun getRecyclerView(): RecyclerView?

    override fun onDestroy() {
        super.onDestroy()
        viewModel.adapterSavedPosition =
            (getRecyclerView()?.layoutManager as LinearLayoutManager?)
                ?.findLastCompletelyVisibleItemPosition() ?: 0
    }
}