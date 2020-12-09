package com.wsiz.projekt_zespolowy.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.fragment.BaseFragment

abstract class UserFragment<Binding : ViewDataBinding, VM : UserViewModel> : BaseFragment<VM>() {

    private lateinit var binding: Binding

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelState()
    }

    private fun observeViewModelState() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when(it) {
                null -> return@Observer
                is UserViewModel.State.ErrorLoading -> {
                    val context = context ?: return@Observer
                    Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
                }
                else -> onViewModelStateChanged(it)
            }
        })
    }

    protected open fun onViewModelStateChanged(state: UserViewModel.State) {
    }
}