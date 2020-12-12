package com.wsiz.projekt_zespolowy.base.activity

import androidx.appcompat.app.AppCompatActivity
import com.wsiz.projekt_zespolowy.base.view_model.BaseViewModel

abstract class BaseActivity<ViewModel : BaseViewModel<*>> : AppCompatActivity() {

    abstract val viewModel: ViewModel

    override fun onDestroy() {
        super.onDestroy()

        viewModel.onViewDestroyed()
    }
}