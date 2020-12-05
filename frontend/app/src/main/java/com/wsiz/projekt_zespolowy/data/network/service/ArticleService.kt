package com.wsiz.projekt_zespolowy.data.network.service

import com.wsiz.projekt_zespolowy.data.dto.Article
import com.wsiz.projekt_zespolowy.data.network.Endpoints
import com.wsiz.projekt_zespolowy.data.dto.Post
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ArticleService {

    @GET(Endpoints.GET_ALL_ARTICLES)
    fun getAll(@Query(Endpoints.PAGE_NUMBER_PARAM) pageNumber: Int): Single<List<Article>>
}