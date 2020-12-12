package com.wsiz.projekt_zespolowy.activity.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.wsiz.projekt_zespolowy.base.fragment.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.Login
import com.wsiz.projekt_zespolowy.data.repository.UserRepository
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences

class LoginViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userRepository: UserRepository
) : BaseViewModel<LoginViewModel.State>() {

    enum class State {
        NO_LOGIN_AND_PASSWORD, NO_LOGIN, NO_PASSWORD, LOADING, ERROR, SUCCESS
    }

    override val state = MutableLiveData<State>()

    fun login(username: String, password: String) {
        when {
            username.isEmpty() and password.isEmpty() -> {
                state.postValue(State.NO_LOGIN_AND_PASSWORD)
            }
            username.isEmpty() -> {
                state.postValue(State.NO_LOGIN)
            }
            password.isEmpty() -> {
                state.postValue(State.NO_PASSWORD)
            }
            else -> {
                state.postValue(State.LOADING)
                login(Login(username, password))
            }
        }
    }

    private fun login(login: Login) {
        userRepository.login(login).io({
            val userLoginResponse = it.body()
            val token = it.headers()["Authorization"]
            if (userLoginResponse == null || token == null) {
                state.postValue(State.ERROR)
                return@io
            }

            sharedPreferences.putUserId(userLoginResponse.id)
            sharedPreferences.putToken(token)
            sharedPreferences.putUserName(userLoginResponse.name)
            state.postValue(State.SUCCESS)
        }, { state.postValue(State.ERROR) })
    }
}