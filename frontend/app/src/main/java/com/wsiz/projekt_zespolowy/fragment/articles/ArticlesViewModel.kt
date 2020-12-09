package com.wsiz.projekt_zespolowy.fragment.articles

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ConcatAdapter
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.HeaderRecycleViewAdapter
import com.wsiz.projekt_zespolowy.base.fragment.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.Article
import com.wsiz.projekt_zespolowy.data.repository.ArticleRepository
import io.reactivex.Single

class ArticlesViewModel @ViewModelInject constructor(private val articleRepository: ArticleRepository) :
    BaseViewModel<ArticlesViewModel.State>(),
    ArticlesRecyclerViewAdapter.ArticleInteractionContract {

    private var recyclerViewAdapter: ConcatAdapter? = null

    override val state = MutableLiveData<State>()

    sealed class State {
        class LoadingError(val error: Throwable) : State()
        class PostClick(val cardView: CardView, val titleView: TextView, val contentView: TextView, val separatorView: View, val article: Article) :
            State()
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

    override fun onClick(cardView: CardView, titleView: TextView, contentView: TextView, separatorView: View, article: Article) {
        state.postValue(State.PostClick(cardView, titleView, contentView, separatorView, article))
    }

    override fun loadMoreData(pageNumber: Int): Single<List<Article>> {
        return loadArticles(pageNumber)
    }

    override fun onErrorLoading(error: Throwable) {
        state.postValue(State.LoadingError(error))
    }

    private fun loadArticles(pageNumber: Int): Single<List<Article>> {
        return articleRepository.getAll(pageNumber)
    }
}