package com.wsiz.projekt_zespolowy.fragment.edit_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.wsiz.projekt_zespolowy.base.fragment.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.EditPost
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.network.FirebaseStorageService
import com.wsiz.projekt_zespolowy.data.repository.PostRepository

class EditPostViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository,
    private val firebaseStorageService: FirebaseStorageService
) : BaseViewModel<EditPostViewModel.State>() {

    enum class State {
        INIT, NO_DESCRIPTION, LOADING, ERROR, SUCCESS, REMOVED
    }

    override val state = MutableLiveData<State>()

    fun editPost(post: Post, description: String) {
        when {
            description.isEmpty() -> {
                state.postValue(State.NO_DESCRIPTION)
            }
            else -> {
                state.postValue(State.LOADING)
                editPost(post.apply { this.description = description })
            }
        }
    }

    fun removePost(post: Post) {
        state.postValue(State.LOADING)

        firebaseStorageService.removePostImage(post.uuid) {
            postRepository.delete(post.id).io(
                { state.postValue(State.REMOVED) },
                { state.postValue(State.ERROR) }
            )
        }
    }

    private fun editPost(post: Post) {
        postRepository.update(EditPost.map(post)).io({
            state.postValue(State.SUCCESS)
        }, {
            state.postValue(State.ERROR)
        })
    }
}