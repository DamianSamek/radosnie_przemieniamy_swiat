package com.wsiz.projekt_zespolowy.fragment.edit_post

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.wsiz.projekt_zespolowy.base.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.EditPost
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.network.FirebaseStorageService
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.utils.SavedStateHandleHelper

class EditPostViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository,
    private val firebaseStorageService: FirebaseStorageService,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<EditPostViewModel.State>() {

    enum class State {
        INIT, NO_DESCRIPTION, LOADING, ERROR, SUCCESS, NO_CHANGES, REMOVED
    }

    override val state = MutableLiveData<State>()

    val post = SavedStateHandleHelper.safeArgs<EditPostFragmentArgs>(savedStateHandle).post

    fun editPost(description: String) {
        when {
            description.isEmpty() -> {
                state.postValue(State.NO_DESCRIPTION)
            }
            else -> {
                if(post.description == description) {
                    state.postValue(State.NO_CHANGES)
                } else {
                    state.postValue(State.LOADING)
                    editPost(post.apply { this.description = description })
                }
            }
        }
    }

    fun removePost() {
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