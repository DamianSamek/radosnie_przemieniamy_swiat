package com.wsiz.projekt_zespolowy.fragment.articles

import androidx.cardview.widget.CardView
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ConcatAdapter
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.HeaderRecycleViewAdapter
import com.wsiz.projekt_zespolowy.base.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.Article
import com.wsiz.projekt_zespolowy.data.repository.ArticleRepository
import io.reactivex.Single

class ArticlesViewModel @ViewModelInject constructor(private val articleRepository: ArticleRepository) :
    BaseViewModel(), ArticlesRecyclerViewAdapter.ArticleInteractionContract {

    private var recyclerViewAdapter: ConcatAdapter? = null

    val state = MutableLiveData<ArticleRecyclerViewAdapterEvent>()

    sealed class ArticleRecyclerViewAdapterEvent {
        class Error(val error: Throwable) : ArticleRecyclerViewAdapterEvent()
        class Click(val cardView: CardView, val article: Article) :
            ArticleRecyclerViewAdapterEvent()
    }

    override fun onCleared() {
        super.onCleared()

        state.postValue(null)
    }

    fun getAdapter(): ConcatAdapter {
        if (recyclerViewAdapter == null) {
            val articlesAdapter = ArticlesRecyclerViewAdapter(this)
            val headerAdapter = HeaderRecycleViewAdapter()

            recyclerViewAdapter = ConcatAdapter(headerAdapter, articlesAdapter)
        }

        return recyclerViewAdapter!!
    }

    override fun onClick(cardView: CardView, article: Article) {
        state.postValue(ArticleRecyclerViewAdapterEvent.Click(cardView, article))
    }

    override fun loadMoreData(pageNumber: Int): Single<List<Article>> {
        return loadArticles(pageNumber)
    }

    override fun onErrorLoading(error: Throwable) {
        state.postValue(ArticleRecyclerViewAdapterEvent.Error(error))
    }

    private fun loadArticles(pageNumber: Int): Single<List<Article>> {
        return articleRepository.getAll(pageNumber)
    }
}