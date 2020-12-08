package com.wsiz.projekt_zespolowy.fragment.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.activity.main.MainActivity
import com.wsiz.projekt_zespolowy.base.BaseFragment
import com.wsiz.projekt_zespolowy.data.dto.Article
import com.wsiz.projekt_zespolowy.databinding.ArticlesFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment : BaseFragment() {

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                null -> return@Observer
                is ArticlesViewModel.ArticleRecyclerViewAdapterEvent.Error -> {
                    onErrorLoading(it.error)
                }
                is ArticlesViewModel.ArticleRecyclerViewAdapterEvent.Click -> {
                    onArticleClick(it.cardView, it.article)
                }
            }
        })
    }

    private fun onErrorLoading(error: Throwable) {
        val context = context ?: return
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    private fun onArticleClick(cardView: CardView, article: Article) {
        val extras = FragmentNavigatorExtras(
            cardView to "articleTransition"
        )
        val direction =
            ArticlesFragmentDirections.actionArticlesFragmentToOneArticleFragment(article)
        (activity as MainActivity).navigateTo(direction, extras)
    }
}