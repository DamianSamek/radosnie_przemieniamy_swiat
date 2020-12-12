package com.wsiz.projekt_zespolowy.fragment.user

import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.base.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.base.view_model.ViewModelActionObservable
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.BasePostsAdapter
import com.wsiz.projekt_zespolowy.base.view_model.BaseStatefulRecyclerViewViewModel
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import io.reactivex.Single

abstract class UserViewModel(
    private val userId: Int,
    private val postRepository: PostRepository
) : BaseStatefulRecyclerViewViewModel<UserViewModel.State>(), BasePostsAdapter.PostInteractionContract {

    // Cannot use sealed class because need additional <OpenAddPost out State> class in ThisUserViewModel. Issue: https://youtrack.jetbrains.com/issue/KT-13495
    open class State {
        class PostClick(val cardView: CardView, val userPost: UserPost) : State()
        class ErrorLoading(error: Throwable) : State()
    }

    var adapter: RecyclerView.Adapter<*>? = null

    override val state = MutableLiveData<State>()

    private fun loadPosts(userId: Int, pageNumber: Int): Single<List<UserPost>> {
        return postRepository.getByUser(userId, pageNumber)
    }

    private fun like(postId: Int) {
        postRepository.like(postId)
    }

    fun getRecyclerViewAdapter(): RecyclerView.Adapter<*> {
        if (adapter == null) {
            adapter = buildRecycleViewAdapter()
        }

        return adapter!!
    }

    protected abstract fun buildRecycleViewAdapter(): RecyclerView.Adapter<*>

    override fun onPostClick(cardView: CardView, userPost: UserPost) {
        state.postValue(State.PostClick(cardView, userPost))
    }

    override fun onLikeClick(userPost: UserPost) {
        like(userPost.id)
    }

    override fun onErrorLoading(error: Throwable) {
        state.postValue(State.ErrorLoading(error))
    }

    override fun loadMoreData(pageNumber: Int): Single<List<UserPost>> {
        return loadPosts(userId, pageNumber)
    }

    override fun onViewModelAction(viewModelAction: ViewModelActionObservable.ViewModelAction) {
        if (viewModelAction is ViewModelActionObservable.ViewModelAction.UserPostsUpdated) {
            adapter = null
        }
    }
}