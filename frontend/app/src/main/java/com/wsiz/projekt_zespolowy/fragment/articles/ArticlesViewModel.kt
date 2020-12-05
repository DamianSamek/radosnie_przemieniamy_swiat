package com.wsiz.projekt_zespolowy.fragment.articles

import androidx.hilt.lifecycle.ViewModelInject
import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.Article
import com.wsiz.projekt_zespolowy.data.repository.ArticleRepository
import io.reactivex.Single

class ArticlesViewModel @ViewModelInject constructor(private val articleRepository: ArticleRepository) :
    BaseViewModel() {

    fun loadArticles(pageNumber: Int): Single<List<Article>> {
        return articleRepository.getAll(pageNumber)
    }
}