package com.wsiz.projekt_zespolowy.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.BaseFragment
import com.wsiz.projekt_zespolowy.base.BasePostsAdapter
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import io.reactivex.Single

abstract class UserFragment<Binding : ViewDataBinding, VM : UserViewModel> : BaseFragment(),
    BasePostsAdapter.PostInteractionContract {

    private lateinit var binding: Binding

    abstract fun getViewModel(): VM

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun showRecyclerViewHeaderView(): Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, getViewModel())
        binding.setVariable(BR.adapter, BasePostsAdapter(this, showRecyclerViewHeaderView()))
        return binding.root
    }

    //    PostRecyclerViewAdapter callbacks
    override fun loadMoreData(pageNumber: Int): Single<List<UserPost>> {
        return getViewModel().loadPosts(getUserId(), pageNumber)
    }

    abstract fun getUserId(): Int

    override fun onErrorLoading(error: Throwable) {
        val context = context ?: return
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun onLikeClick(userPost: UserPost) {
        getViewModel().like(userPost.id)
    }
}