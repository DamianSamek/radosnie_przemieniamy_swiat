package com.wsiz.projekt_zespolowy.data.repository

import com.wsiz.projekt_zespolowy.data.dto.Login
import com.wsiz.projekt_zespolowy.data.dto.UserLoginResponse
import com.wsiz.projekt_zespolowy.data.network.service.AuthenticationService
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val authenticationService: AuthenticationService) {

    fun login(login: Login): Single<Response<UserLoginResponse>> {
        return authenticationService.login(login)
    }
}