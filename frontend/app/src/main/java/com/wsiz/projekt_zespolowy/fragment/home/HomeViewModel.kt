package com.wsiz.projekt_zespolowy.fragment.home

import androidx.cardview.widget.CardView
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ConcatAdapter
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.BasePostsAdapter
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.HeaderRecycleViewAdapter
import com.wsiz.projekt_zespolowy.base.fragment.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import io.reactivex.Single

class HomeViewModel @ViewModelInject constructor(private val postRepository: PostRepository) :
    BaseViewModel<HomeViewModel.State>(), BasePostsAdapter.PostInteractionContract {

    sealed class State {
        class OnPostClick(val cardView: CardView, val userPost: UserPost) : State()
        class ErrorLoading(val error: Throwable) : State()
    }

    override val state = MutableLiveData<State>()

    private var adapter: ConcatAdapter? = null

    private fun loadPosts(pageNumber: Int): Single<List<UserPost>> {
        return postRepository.getAll(pageNumber)
    }

    private fun like(postId: Int) {
        postRepository.like(postId)
    }

    fun getAdapter(): ConcatAdapter {
        if (adapter == null) {
            val postsAdapter = BasePostsAdapter(this)
            val headerAdapter = HeaderRecycleViewAdapter()

            adapter = ConcatAdapter(headerAdapter, postsAdapter)
        }

        return adapter!!
    }

    override fun onPostClick(cardView: CardView, userPost: UserPost) {
        state.postValue(State.OnPostClick(cardView, userPost))
    }

    override fun onLikeClick(userPost: UserPost) {
        like(userPost.id)
    }

    override fun loadMoreData(pageNumber: Int): Single<List<UserPost>> {
        return loadPosts(pageNumber)
    }

    override fun onErrorLoading(error: Throwable) {
        state.postValue(State.ErrorLoading(error))
    }
}