package com.wsiz.projekt_zespolowy.activity.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wsiz.projekt_zespolowy.data.dto.Login
import com.wsiz.projekt_zespolowy.data.repository.UserRepository
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userRepository: UserRepository
) :
    ViewModel() {

    enum class State {
        INIT, NO_LOGIN, NO_PASSWORD, LOADING, ERROR, SUCCESS
    }

    private var loginDisposable: Disposable? = null

    val state = MutableLiveData<State>().apply { postValue(State.INIT) }

    fun login(username: String, password: String) {
        when {
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
        loginDisposable = userRepository.login(login).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val userLoginResponse = it.body()
                val token = it.headers()["Authorization"]
                if (userLoginResponse == null || token == null) {
                    state.postValue(State.ERROR)
                    return@subscribe
                }

                sharedPreferences.putUserId(userLoginResponse.id)
                sharedPreferences.putToken(token)
                state.postValue(State.SUCCESS)
            }, { state.postValue(State.ERROR) })
    }

    override fun onCleared() {
        super.onCleared()
        loginDisposable?.dispose()
    }
}