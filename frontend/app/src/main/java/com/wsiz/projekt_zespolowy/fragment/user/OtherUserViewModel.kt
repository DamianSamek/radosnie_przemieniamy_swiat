package com.wsiz.projekt_zespolowy.fragment.user

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.BasePostsAdapter
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.HeaderRecycleViewAdapter
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.utils.SavedStateHandleHelper.safeArgs

class OtherUserViewModel @ViewModelInject constructor(
    userPostRepository: PostRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : UserViewModel(
    safeArgs<OtherUserFragmentArgs>(savedStateHandle).userId,
    userPostRepository
) {

    override fun buildRecycleViewAdapter(): RecyclerView.Adapter<*> {
        val headerAdapter = HeaderRecycleViewAdapter()
        val postsAdapter = BasePostsAdapter(this)

        return ConcatAdapter(headerAdapter, postsAdapter)
    }

    fun getPostsAdapter() = (getRecyclerViewAdapter() as ConcatAdapter).adapters[1] as BasePostsAdapter
}