package com.wsiz.projekt_zespolowy.fragment.one_article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import com.wsiz.projekt_zespolowy.databinding.OneArticleFragmentLayoutBinding
import javax.inject.Inject

class OneArticleFragment : Fragment() {

    @Inject
    lateinit var sp: SharedPreferences

    private lateinit var binding: OneArticleFragmentLayoutBinding
    private val viewModel: OneArticleViewModel by viewModels()

    private val navArguments: OneArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.one_article_fragment_layout, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.article, navArguments.article)
        return binding.root
    }
}