package com.wsiz.projekt_zespolowy.base.fragment

import androidx.fragment.app.Fragment
import com.wsiz.projekt_zespolowy.activity.main.MainActivity
import com.wsiz.projekt_zespolowy.base.view_model.BaseViewModel

abstract class BaseFragment<ViewModel : BaseViewModel<*>> : Fragment() {

    protected abstract val viewModel: ViewModel
    fun mainActivity() = activity as MainActivity

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onViewDestroyed()
    }
}