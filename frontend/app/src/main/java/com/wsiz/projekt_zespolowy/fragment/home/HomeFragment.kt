package com.wsiz.projekt_zespolowy.fragment.home

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
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.databinding.HomeFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single

@AndroidEntryPoint
class HomeFragment : Fragment(), PostsRecyclerViewAdapter.PostInteractionContract {

    private lateinit var binding: HomeFragmentLayoutBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment_layout, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.adapter, PostsRecyclerViewAdapter(this))
        return binding.root
    }

    override fun loadMoreData(pageNumber: Int): Single<List<UserPost>> {
        return viewModel.loadPosts(pageNumber)
    }

    override fun onErrorLoading(error: Throwable) {
        val context = context ?: return
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun onPostClick(userPost: UserPost) {
        (activity as MainActivity).navigateTo(
            HomeFragmentDirections.actionHomeFragmentToUserFragment(
                userPost.userId
            )
        )
    }
}