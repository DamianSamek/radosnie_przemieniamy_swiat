package com.wsiz.projekt_zespolowy.data.network.service

import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.dto.UserPostResponse
import com.wsiz.projekt_zespolowy.data.network.Endpoints
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface PostService {

    @POST(Endpoints.CREATE_POST)
    fun create(@Body post: Post): Completable

    @DELETE(Endpoints.DELETE_POST)
    fun delete(@Path(Endpoints.ID_PARAM) postId: Int): Completable

    @PUT(Endpoints.UPDATE_POST)
    fun update(@Body post: Post): Completable

    @GET(Endpoints.GET_ALL_POSTS)
    fun getAll(@Query(Endpoints.PAGE_NUMBER_PARAM) pageNumber: Int): Single<List<UserPostResponse>>

    @GET(Endpoints.GET_USER_POSTS)
    fun getByUser(
        @Path(Endpoints.ID_PARAM) userId: Int,
        @Query(Endpoints.PAGE_NUMBER_PARAM) pageNumber: Int
    ): Single<List<UserPostResponse>>

    @POST(Endpoints.LIKE_POST)
    fun like(@Path(Endpoints.ID_PARAM) userId: Int): Completable
}