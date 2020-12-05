package com.wsiz.projekt_zespolowy.data.network.service

import com.wsiz.projekt_zespolowy.data.dto.Login
import com.wsiz.projekt_zespolowy.data.dto.UserLoginResponse
import com.wsiz.projekt_zespolowy.data.network.Endpoints
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST(Endpoints.LOGIN)
    fun login(@Body login: Login): Single<Response<UserLoginResponse>>
}