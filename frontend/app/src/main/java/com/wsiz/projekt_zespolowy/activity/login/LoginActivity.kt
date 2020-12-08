package com.wsiz.projekt_zespolowy.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.activity.main.MainActivity
import com.wsiz.projekt_zespolowy.databinding.LoginActivityLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: LoginActivityLayoutBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity_layout)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)

        observeLoginState()
    }

    private fun observeLoginState() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                null, LoginViewModel.State.INIT, LoginViewModel.State.LOADING -> return@Observer
                LoginViewModel.State.NO_LOGIN -> {
                    Toast.makeText(
                        applicationContext,
                        R.string.login_no_username,
                        Toast.LENGTH_LONG
                    ).show()
                }
                LoginViewModel.State.NO_PASSWORD -> {
                    Toast.makeText(
                        applicationContext,
                        R.string.login_no_password,
                        Toast.LENGTH_LONG
                    ).show()
                }
                LoginViewModel.State.ERROR -> {
                    Toast.makeText(applicationContext, R.string.error, Toast.LENGTH_LONG).show()
                }
                LoginViewModel.State.SUCCESS -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}