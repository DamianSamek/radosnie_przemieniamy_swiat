package com.wsiz.projekt_zespolowy.fragment.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.fragment.BaseStatefulRecyclerViewFragment
import com.wsiz.projekt_zespolowy.data.dto.Article
import com.wsiz.projekt_zespolowy.databinding.ArticlesFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment : BaseStatefulRecyclerViewFragment<ArticlesViewModel>() {

    private lateinit var binding: ArticlesFragmentLayoutBinding
    override val viewModel: ArticlesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                is ArticlesViewModel.State.LoadingError -> {
                    onErrorLoading(it.error)
                }
                is ArticlesViewModel.State.PostClick -> {
                    onArticleClick(
                        it.cardView,
                        it.titleView,
                        it.contentView,
                        it.separatorView,
                        it.article
                    )
                }
            }
        })
    }

    private fun onErrorLoading(error: Throwable) {
        val context = context ?: return
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    private fun onArticleClick(
        cardView: CardView,
        titleView: TextView,
        contentView: TextView,
        separatorView: View,
        article: Article
    ) {
        val extras = FragmentNavigatorExtras(
            cardView to "articleTransition",
            titleView to "articleTransitionTitle",
            contentView to "articleTransitionContent",
            separatorView to "articleTransitionSeparator"
        )
        val direction =
            ArticlesFragmentDirections.actionArticlesFragmentToOneArticleFragment(article)
        mainActivity().navigateTo(direction, extras)
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }
}