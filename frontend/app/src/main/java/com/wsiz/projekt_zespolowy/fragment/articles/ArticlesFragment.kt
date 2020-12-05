package com.wsiz.projekt_zespolowy.fragment.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.activity.MainActivity
import com.wsiz.projekt_zespolowy.data.dto.Article
import com.wsiz.projekt_zespolowy.databinding.ArticlesFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single

@AndroidEntryPoint
class ArticlesFragment : Fragment(), ArticlesRecyclerViewAdapter.ArticleInteractionContract {

    private lateinit var binding: ArticlesFragmentLayoutBinding
    private val viewModel: ArticlesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.articles_fragment_layout, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.adapter, ArticlesRecyclerViewAdapter(this))
        return binding.root
    }

    override fun loadMoreData(pageNumber: Int): Single<List<Article>> {
        return viewModel.loadArticles(pageNumber)
    }

    override fun onErrorLoading(error: Throwable) {
        val context = context ?: return
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(article: Article) {
        val direction =
            ArticlesFragmentDirections.actionArticlesFragmentToOneArticleFragment(article)
        (activity as MainActivity).navigateTo(direction)
    }
}