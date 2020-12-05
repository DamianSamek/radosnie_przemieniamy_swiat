package com.wsiz.projekt_zespolowy.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.activity.MainActivity
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import com.wsiz.projekt_zespolowy.databinding.UserFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment(), PostsRecyclerViewAdapter.PostAdapterContract {

    @Inject
    lateinit var sp: SharedPreferences

    private lateinit var binding: UserFragmentLayoutBinding
    private val viewModel: UserViewModel by viewModels()

    private val navArguments: UserFragmentArgs by navArgs()
    private val userId
        get() = if (navArguments.userId == -1) sp.getUserId() else navArguments.userId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.user_fragment_layout, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.adapter, PostsRecyclerViewAdapter(this))
        return binding.root
    }

    //    PostRecyclerViewAdapter callbacks
    override fun loadMoreData(pageNumber: Int): Single<List<UserPost>> {
        return viewModel.loadPosts(userId, pageNumber)
    }

    override fun onErrorLoading(error: Throwable) {
        val context = context ?: return
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun onPostClick(post: UserPost) {
        if (post.userId == sp.getUserId()) {
            val direction = UserFragmentDirections.actionUserFragmentToEditPostFragment(Post.map(post))
            (activity as MainActivity).navigateTo(direction)
        }
    }
}