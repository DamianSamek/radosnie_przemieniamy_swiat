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
import com.wsiz.projekt_zespolowy.base.BasePostsAdapter
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import com.wsiz.projekt_zespolowy.databinding.HomeFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), BasePostsAdapter.PostInteractionContract {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

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
        binding.setVariable(BR.adapter, BasePostsAdapter(this))
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
        if (sharedPreferences.getUserId() == userPost.userId) {
            (activity as MainActivity).navigateTo(
                HomeFragmentDirections.actionHomeFragmentToThisUserFragment()
            )
        } else {
            (activity as MainActivity).navigateTo(
                HomeFragmentDirections.actionHomeFragmentToOtherUserFragment(userPost.userId)
            )
        }
    }

    override fun onLikeClick(userPost: UserPost) {
        viewModel.like(userPost.id)
    }
}