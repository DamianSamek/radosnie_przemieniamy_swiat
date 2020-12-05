package com.wsiz.projekt_zespolowy.data.repository

import com.wsiz.projekt_zespolowy.data.dto.Article
import com.wsiz.projekt_zespolowy.data.network.service.ArticleService
import io.reactivex.Single
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val articleService: ArticleService) {

    fun getAll(pageNumber: Int): Single<List<Article>> {
        return articleService.getAll(pageNumber)
    }
}