package com.wsiz.projekt_zespolowy.data.network.service

import com.wsiz.projekt_zespolowy.data.network.Endpoints
import com.wsiz.projekt_zespolowy.data.services.Post
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {

    @POST(Endpoints.CREATE_POST)
    fun create(@Body post: Post): Completable

    @GET(Endpoints.GET_ALL_POSTS)
    fun getAll(): Single<Post>

    @GET(Endpoints.GET_USER_POSTS)
    fun getByUser(@Path(Endpoints.ID_PARAM) userId: Int): Single<Post>
}